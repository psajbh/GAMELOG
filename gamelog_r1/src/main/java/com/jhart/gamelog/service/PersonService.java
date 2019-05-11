package com.jhart.gamelog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.dao.PersonDao;
import com.jhart.gamelog.entities.person.EntityPerson;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.model.Person;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class PersonService {
	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
	
	public boolean persist(List<Person> personList) {
		LOG.debug("PersonService : persist - potentially " + ((personList == null) ? 0 : personList.size()) + " records");
		boolean rValue = false;
		
		Map<String, EntityPerson> persistedPersons = new HashMap<>();
		List<Person> saveList = new ArrayList<>();
		
		PersonDao gle = new PersonDao();
        for(EntityPerson entityPerson : gle.getPersistedPersons()) {
        	persistedPersons.put(entityPerson.getCode(), entityPerson);
        }

        LOG.debug("PesonService : persist number of persisted persons: " + persistedPersons.size());
        
        if (persistedPersons.size() > 0) {
        	LOG.debug("PersonService : persist - persisting partial number person objects");
        	for (Person person : personList) {
        		if (persistedPersons.get(person.getId())==null){
        			saveList.add(person);
        		}
        	}
        }
        else {
        	LOG.debug("PersonService : persist - persisting all person objects");
        	saveList.addAll(personList);
        }
        
        if (saveList.size() > 0) {
        	LOG.debug("saveLists size: " + saveList.size());
        	try {
        		gle.saveAll(saveList);
        		rValue = true;
        	}
            catch(NullSessionException nse) {
                LOG.error("failed to establish a hibernate session", nse);
            }
            catch(DbExecutionException dbe) {
                LOG.error("failed to execute db functionality: " + dbe.getMessage()  );
            }
        }
        LOG.debug("PersonService : persist - finished with rValue: " + rValue);
		return rValue;
	}
}
