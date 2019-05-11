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
import com.jhart.gamelog.model.Person;
import com.jhart.gamelog.utils.Utils;

public class PlayerParser implements Parser<Person>{
	private static final Logger LOG = LoggerFactory.getLogger(PlayerParser.class);
    private static String START = "/PLAYERS_";
    private static String END = ".txt";
    
    
    @Override
    public Map<String, List<Person>> process(String source) {
        Map<String, List<Person>> personMap = new HashMap<>();
        List<Person> persons = new ArrayList<>();
        
        String seasonSetup = source.substring(source.indexOf(PlayerParser.START) + PlayerParser.START.length());
        String season = seasonSetup.substring(0, PlayerParser.END.length());
        
        Path p1 = Paths.get(source);
        try (BufferedReader reader = Files.newBufferedReader(p1, Charset.forName(Parser.UTF_8))){
            String currentLine = null;
            
            while((currentLine=reader.readLine()) != null) {
            	long number = 1;
                if (null != currentLine) {
                	LOG.trace("processing: " + currentLine);
                	currentLine = Utils.cleanLine(currentLine, Parser.CLEAN_REPEAT);
                	if(StringUtils.isBlank(currentLine)) {
                		break;
                	}
                    Person person = new Person();
                    String[] elements = currentLine.split(",");
                    int max = elements.length;
                    
                    person.setId(elements[0]);
                    person.setLastName(Utils.stripQuotes(elements[1]));
                    person.setFirstName(Utils.stripQuotes(elements[2]));
                    String name = String.format("%s %s", person.getFirstName(), person.getLastName());
                    person.setName(name);
                    
                    person.setPlayerDebut(processDebutDate(elements[3]));
                    person.setManagerDebut(processDebutDate(elements[4]));
                    person.setCoachDebut(processDebutDate(elements[5]));
                    if(max == 7) {
                    	person.setUmpireDebut(processDebutDate(elements[6]));
                    }
                    else {
                    	person.setUmpireDebut(null);
                    }
                    
                    persons.add(person);
                    LOG.trace("processed player: " + number++);
                }
            }
            
            personMap.put(season, persons);
            
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return personMap;
    }
    
    private String processDebutDate(String debutDate) {
        if (debutDate.equals(Parser.EMPTY)) {
            return null;
        }
        else {
            return debutDate;
        }
    }

}
