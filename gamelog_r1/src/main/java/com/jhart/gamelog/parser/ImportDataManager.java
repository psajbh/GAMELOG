package com.jhart.gamelog.parser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.slf4j.logger;
//import org.slf4j.loggerFactory;
//import com.jhart.gamelog.GamelogParseInitializer;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.Person;
import com.jhart.gamelog.model.parks.Park;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.service.GamelogService;
import com.jhart.gamelog.service.ParkService;
import com.jhart.gamelog.service.PersonService;
import com.jhart.gamelog.service.TeamService;
import com.jhart.gamelog.utils.appconfig.AppConfig;
import com.jhart.gamelog.utils.appconfig.AppUtil;
import com.jhart.gamelog.utils.dbconfig.DbUtil;
import com.jhart.gamelog.utils.dbconfig.exception.DbConfigException;

import lombok.extern.slf4j.Slf4j;

/**
 * ImportDataManager - manages the parsing of incoming data.
 */
@Slf4j
public class ImportDataManager {
	private static final String DB_PROP_FILE = "db-config.properties";
	private static final String CONFIG_PROP_FILE = "config.properties";
    
    //good ref on intializing arrays: https://stackoverflow.com/questions/1200621/how-do-i-declare-and-initialize-an-array-in-java
    // the following files need to be provided from an external file.
	
	private String control = null;
    private String playerSource = null;
    private String teamSource = null;
    private String parkSource = null;
    private String gameLogSource = null;
    
    private static Map<String, List<Gamelog>> gamelogRep = new HashMap<>();
    private static Map<String, List<Person>> personRep = new HashMap<>();
    private static Map<String, Person> personMap = new HashMap<>();
    private static Map<String, List<TeamSeason>> teamRep = new HashMap<>();
    private static Map<String, List<Park>> parkRep = new HashMap<>();
    
    private boolean dbSetupOk = false;
    
    private ParkService parkService= new ParkService();
    private TeamService teamService = new TeamService();
    private PersonService personService = new PersonService();
    private GamelogService gamelogService = new GamelogService();
    
    public ImportDataManager(String control) {
    	log.debug("ImportDataManager-constructor: control: " + control);
    	
    	initConfig();
    	
    	if (null != control) {
    		validateControl(control);
    	}
    	else {
    		control = AppConfig.getControl();
    		validateControl(control);
    		log.debug("ImportDataManager-constructor: control generated from configuration default");    		
    	}
    	
    	initDataBaseConfig();
    	setSources();
    } 
    
    private void setSources() {
    	log.trace("setSources: path: " + AppConfig.getParseDataSourcePath());
    	playerSource = String.format(AppConfig.getParseDataSourcePath() + "/PLAYERS_%s.txt", getControl());
    	teamSource = String.format(AppConfig.getParseDataSourcePath() + "/TEAM_%s.txt", getControl());
    	parkSource = String.format(AppConfig.getParseDataSourcePath() + "/PARKS_%s.txt", getControl());
    	gameLogSource = String.format(AppConfig.getParseDataSourcePath() + "/GL_%s.txt", getControl());
    }

    /**
     * validateControl insures the control value representing a season is a valid year.
     * @param control - String
     * @throws gamelogException - GamelogException 
     */
    private void validateControl(String control) throws GamelogException{
    	log.trace("validateControl: validating control: " + control);
        try {
            int year = Integer.valueOf(control);
            LocalDate aDate = LocalDate.now();
            LocalDate bDate = aDate.withYear(year);
            int validYear = bDate.getYear();
            if (validYear > 1850 && validYear <= aDate.getYear()-1) {
                log.trace("validateControl: - " + control + " is valid");
                setControl(control);
            }
            else {
                GamelogException gamelogException = new GamelogException();
                gamelogException.setMessage("invalid control value");
                throw gamelogException;
            }
        }
        catch(Exception e) {
            GamelogException gamelogException = new GamelogException();
            gamelogException.setMessage(e.getMessage());
            throw gamelogException;
        }
    }
    
    private String getControl() {
    	return this.control;
    }
    
    private void setControl(String control) {
    	this.control = control;
    }
    
    private void initConfig() {
    	log.debug("initConfig: - start");
    	try {
    		AppUtil.setup(CONFIG_PROP_FILE);
    	}
    	catch(Exception e) {
    		log.error("initConfig: - failure: " + e.getMessage(), e);
    	}
    }
    
    private void initDataBaseConfig(){
        log.debug("initDataBaseConfig: start");
        try {
            DbUtil.setup(DB_PROP_FILE);
            this.dbSetupOk = true;
            log.debug("initDataBaseConfig: - database setup complete");
        }
        catch(DbConfigException dbConfigException) {
            log.error("initDataBaseConfig: failure: " + dbConfigException.getMessage(), dbConfigException);
        }
    }
    
    //reference- https://www.testingexcellence.com/4-different-ways-iterate-map-java/
    public void executeGameLogDataImport(){
    	log.debug("executeGamelogDataImport: - start");
        if (!dbSetupOk) {
        	log.warn("executeGamelogDataImport: - dbSetupOk: " + dbSetupOk);
            return;
        }
        GameParser gameParser = new GameParser();
        
        log.debug("executeGamelogDataImport: - process gameLogSource start");
        Map<String, List<Gamelog>> gamelogForSeason = gameParser.process(gameLogSource);
        log.debug("executeGamelogDataImport: - process gamelogSource finish");
        
        for(Map.Entry<String, List<Gamelog>> entry : gamelogForSeason.entrySet()) {
        	gamelogRep.put(entry.getKey(), entry.getValue());
            String msg = String.format("executeGamelogDataImport: added %s gamelogs for season: %s", entry.getValue().size(), entry.getKey());
            log.debug(msg);
        }
        
        if (gamelogRep.size() > 0) {
        	persistGameLogRep();
        }
    }
    
	public void executePersonDataImport() {
		log.debug("executePersonDataImport: - start");
		if (!dbSetupOk) {
			log.warn("executePersonDataImport: - dbSetupOk: " + dbSetupOk);
			return;
		}

		PlayerParser playerParser = new PlayerParser();
		personRep = playerParser.process(playerSource);
		
		for (Person person : personRep.get(getControl())){
			personMap.put(person.getId(), person);
		}
		
		if (personRep.size() > 0) {
			persistPersonRep();
		}
		else {
			log.error("failed to capture any person objects");
		}
	}
    
    public void executeTeamDataImport() {
    	log.debug("executeTeamDataImport: - start");
        if (!dbSetupOk) {
        	log.warn("executeTeamDataImport: - dbSetupOk: " + dbSetupOk);
            return;
        }
        
    	TeamParser teamParser = new TeamParser();
    	log.debug("executeTeamDataImport: processing teamSource: " + teamSource);
    	long start = System.currentTimeMillis();
    	teamRep = teamParser.process(teamSource);
    	long end = System.currentTimeMillis();
    	log.debug("executeTeamDataImport: team processing complete in " + (end - start) + " millis.  Records created: " + (teamRep.values().iterator().next().size()));
    	
    	if (teamRep.size()>0) {
    	    persistTeamRep();
    	}
    }
    
    public void executeParkDataImport() {
    	log.info("executeParkDataImport: processing ParkData");
        if (!dbSetupOk) {
        	log.warn("executeParkDataImport: - dbSetupOk: " + dbSetupOk);
            return;
        }
        ParkParser parkParser = new ParkParser();
        log.debug("executeParkDataImport: processing parkSource: " + parkSource);
        long start = System.currentTimeMillis();
        parkRep = parkParser.process(parkSource);
        long end = System.currentTimeMillis();
        log.debug("executeParkDataImport: park processing complete in " + (end - start) + " millis.  Records created: " + (parkRep.values().iterator().next().size()));
        
        if (parkRep.size() > 0) {
            persistParkRep();
        }
    }
    
    @SuppressWarnings("unused")
	private void persistPersonRep() {
    	log.info("persistPersonRep: processing PersonData");
    	List<Person> personList = personRep.get(getControl());
    	boolean success = personService.persist(personList);
    }
    
	private void persistTeamRep() {
        List<TeamSeason> teamList = teamRep.get(getControl());
        boolean success = teamService.persist(teamList);
        log.debug("persistTeamRep: - status: " + (success ? "success" : "failure"));
    }

    private void persistParkRep() {
        List<Park> parkList = parkRep.get(getControl());
        boolean success = parkService.persist(parkList);
        log.debug("persistParkRep: -  status: " + (success ? "success" : "failure"));
    }
    
	private void persistGameLogRep() {
		log.debug("persistGameLogRep - start");
    	List<Gamelog> gamelogList = gamelogRep.get(getControl()); 
    	boolean success = gamelogService.persist(gamelogList);
        log.debug("persistGamelogRep: - finished with status: " + (success ? "success" : "failure"));
    }
}
