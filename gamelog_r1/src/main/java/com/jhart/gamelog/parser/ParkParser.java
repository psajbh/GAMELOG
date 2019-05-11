package com.jhart.gamelog.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.Parser;
import com.jhart.gamelog.model.parks.Park;
import com.jhart.gamelog.types.LeagueType;
import com.jhart.gamelog.utils.Utils;

public class ParkParser implements Parser<Park>{
	private static final Logger LOG = LoggerFactory.getLogger(ParkParser.class);
    private static String START = "/PARKS_";
    private static String END = ".txt";
    private static int MAX = 9;
    //private static String ACTIVE = "Active";
    private static String UD = "UD";

	@Override
    public Map<String, List<Park>> process(String source){
        String seasonSetup = source.substring(source.indexOf(ParkParser.START) + ParkParser.START.length());
        String season = seasonSetup.substring(0, ParkParser.END.length());
        Map<String, List<Park>> parkMap = new HashMap<>();
        List<Park> parks = new ArrayList<>();
        
        Path p1 = Paths.get(source);
        try (BufferedReader reader = Files.newBufferedReader(p1, Charset.forName(Parser.UTF_8))){
            String currentLine = null;
            int recordNumber = 0;
            while((currentLine=reader.readLine()) != null) {
                if (null != currentLine) {
                	recordNumber++;
                	currentLine = Utils.cleanLine(currentLine, Parser.CLEAN_REPEAT);
                	if(StringUtils.isBlank(currentLine)) {
                		LOG.debug("breaking out at recordNumber: " + recordNumber); //make log statement
                		break;
                	}
                	else {
                        String[] elements = currentLine.split(",");
                        LOG.debug("element: " + elements[0] + " size: " + elements.length);
                        if (elements.length != ParkParser.MAX) {
                        	LOG.info("failed to process parkcode: "+ elements[0] + " size: " + elements.length);
                            continue;
                        }
                        parks.add(processElements(elements));
                	}
                }
            }
            
            parkMap.put(season, parks);    
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return parkMap;
    }
	
	private Park processElements(String[] elements) {
		Park park = new Park();
		park.setParkId(elements[0]);
		park.setName(elements[1]);
		
		if (elements[2].equals(Parser.EMPTY)){
			park.setAka(null);
		}
		else {
			park.setAka(elements[2]);
		}
		
		park.setCity(elements[3]);
		
		park.setState(elements[4]);
		park.setStart(Utils.convertMDY_LocalDateString(elements[5]));
		
		if (elements[6].equals(Parser.EMPTY)){
			park.setEnd(null);
		}
		else {
			park.setEnd(Utils.convertMDY_LocalDateString(elements[6]));
		}
		
		String msg = "park processed " + park.getName() + " start: " + park.getStart() + " end: " + park.getEnd();
		LOG.debug(msg);
		
		if (elements[7].equals(Parser.EMPTY)) {
			park.setLeague(LeagueType.valueOf(ParkParser.UD));
		}
		else {
			park.setLeague(LeagueType.valueOf(elements[7]));
		}

		park.setNotes(Utils.stripQuotes(elements[8]));
		LOG.trace(park.toString());
		return park;
	}
	
}
