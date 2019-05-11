package com.jhart.gamelog;

import com.jhart.gamelog.dao.OneToManyTestDAO;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.parser.ImportDataManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameLogParseInitializer {
    private static final Boolean TEST = Boolean.FALSE;
    private static final Boolean TEST2 = Boolean.FALSE;

    public static void main(String[] args) {
    	log.trace("main: - start");
        //String control = "2017";  // when UI is built, this value will be passed in.
    	String control = "2018";
        // importDataManager will be a service in the future
        ImportDataManager importDataManager;
        try {
            importDataManager = new ImportDataManager(control);
        }
        catch(GamelogException e) {
        	log.error("main: - failed to initialize importDataManager: " + e.getMessage());
            return;
        }
        
    	if (TEST) {
    		OneToManyTestDAO dao = new OneToManyTestDAO();
    		dao.saveCart();
    		return;
    	}
    	
    	if (TEST2) {
    		OneToManyTestDAO dao = new OneToManyTestDAO();
    		dao.saveEntityBioStat();
    		return;
    	}
    	
        // build parks
        log.info("main: start processing Parks");
        importDataManager.executeParkDataImport();
        
        log.info("main: start processing Person Data");        
        importDataManager.executePersonDataImport();
        
        log.info("main: start processing Team Data");
        importDataManager.executeTeamDataImport();
        
        log.info("main: start processing GameLog Data");
        importDataManager.executeGameLogDataImport();
        
        log.debug("main: finished");
    }

}
