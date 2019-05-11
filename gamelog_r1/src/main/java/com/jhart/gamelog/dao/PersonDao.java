package com.jhart.gamelog.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.entities.person.EntityPerson;
import com.jhart.gamelog.entities.team.EntityDivision;
import com.jhart.gamelog.entities.team.EntityFranchise;
import com.jhart.gamelog.entities.team.EntityLeague;
import com.jhart.gamelog.entities.team.EntitySeason;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.model.Person;
import com.jhart.gamelog.model.team.Team;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.transform.PersonTransformer;
import com.jhart.gamelog.transform.TeamTransformer;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class PersonDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(PersonDao.class);
    private PersonTransformer transformer = new PersonTransformer();
    
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        LOG.debug("getAll() - get all person entities and match against parsed model");
        List<IModel> persons = new ArrayList<>();
        
        List<EntityPerson> entityPersons = getAllEntityPersons();
        LOG.debug("getAll() - translate existing entity teams to a parsed team");
        if (entityPersons.size() > 0) {
            for(EntityPerson ep: entityPersons) {
                // need to test transformer.
                Person person = (Person)transformer.transform(ep);
                
                persons.add((IModel)person);
            }
        }
        LOG.debug("returning list of Persons objects that are persisted: " + persons.size());
        return persons;
    	
    }
    
    public List<EntityPerson> getPersistedPersons(){
    	LOG.debug("PersonDAO : getPersistedPersons - start");
        List<EntityPerson> persistedPersons = new ArrayList<>();
        try {
            persistedPersons = getAllEntityPersons();
        }
        catch(Exception e) {
            LOG.error("getPersistedPersons: " + e.getMessage());
        }
        LOG.debug("PersonDAO : getPersistedPesons - finish - number of records: " + persistedPersons.size());
        return persistedPersons;
    }
    
    @SuppressWarnings({ "deprecation", "unchecked" })
    private List<EntityPerson> getAllEntityPersons() throws NullSessionException, DbExecutionException{
        LOG.debug("PesonDao : getAllEntityPersons - query for all persisted entity persons");
        List<EntityPerson> entityPersons;
        
        Session session = getSession();
        session.beginTransaction();
        @SuppressWarnings("rawtypes")
        Query query= session.createQuery("from EntityPerson");
        entityPersons = query.list();
        session.getTransaction().commit();
        session.close();
        LOG.debug("PersonDao : getAllEntityPersons - query completed - number of records: " + entityPersons.size());
        return entityPersons;
    }

    public void saveAll(List<Person> modelPersons) throws NullSessionException, DbExecutionException{
    	// transaction start here.
    	LOG.debug("PersonDao : saveAll - modelPerson size: " + modelPersons.size());
    	
    	Session session = getSession();
    	Transaction tx = session.beginTransaction();
    	try {
    		for(Person person : modelPersons) {
    			LOG.debug("PersonDao:saveAll - persisting person: " + person.getId()); //ardd001
    			EntityPerson entityPerson = (EntityPerson)transformer.transform(person);
    			LOG.debug("PersonDao:saveAll - saving entityPerson: " + entityPerson.getFullName());
    			try {
    				save(entityPerson,session);
    			}
    			catch(Exception e) {
    				LOG.error("PersonDao : saveAll - FAILURE persisting: " + entityPerson.getFullName() + " msg: " + e.getMessage(),e);
    			}
    		}
    		tx.commit();
    		session.close();
    	}
    	catch(Exception e) {
    		session.getTransaction().rollback();
    	}
    	finally {
    		if(null != session) {
    			session.close();
    		}
    	}
    }    

    @SuppressWarnings({"unchecked"})
	private void save(EntityPerson person, Session session) throws NullSessionException, DbExecutionException{
    	LOG.trace("PersonDao:save - person: " + person.getCode());
    	
        try {
        	EntityPerson entityPerson = null;
        	Query<EntityPerson> qs = session.getNamedQuery(EntityPerson.NAME_GET_PERSON_FROM_CODE);
        	qs.setParameter("code", person.getCode());
        	entityPerson = (EntityPerson) qs.uniqueResult();
        
        	if (null == entityPerson) {
        		LOG.trace("PersonDao:save - entityPerson is null");
        		Integer id = (Integer) session.save(person);
        		LOG.trace("PersonDao:save - entityPerson saved with id: " + id);
        	}
        	else {
        		LOG.trace("PersonDao:save - entityPerson updating");
        		session.saveOrUpdate(entityPerson);
        		LOG.trace("PersonDao:save - entityPerson succesfully saved");
        	}
        }
        catch(Exception sqle) {  //IdentifierGenerationException; //attempted to assign id from null one-to-one property [com.jhart.gamelog.entities.person.EntityBioStat.personDetail]
            LOG.error("save error - " + sqle.getMessage(), sqle);
            throw new DbExecutionException(sqle);
        }
    }
    
    
    @SuppressWarnings({ "unchecked"})
	public EntityPerson getEntityPerson(String code) throws GamelogException, NullSessionException{
    	
    	
    	if (StringUtils.isBlank(code)) {
    		return null;
    	}
		try {
			Session session = getSession();
			Query<EntityPerson> qPerson = session.getNamedQuery(EntityPerson.NAME_GET_PERSON_FROM_CODE);
			qPerson.setParameter("code", code);
			EntityPerson entityPerson = (EntityPerson) qPerson.uniqueResult();
			if (null != entityPerson) {
				return entityPerson;
			}
			else {
				EntityPerson newEntityPerson = new EntityPerson();
				newEntityPerson.setCode(code);
				Integer id = (Integer) session.save(newEntityPerson);
				newEntityPerson.setPersonId(id);
				return newEntityPerson;
			}
		}
		catch(NullSessionException nullSessionException) {
			LOG.error("PersonDao:getEntityPerson - exception msg: " + nullSessionException.getMessage());
			throw nullSessionException;
		}
		catch(RuntimeException re) {
			LOG.error("PersonDao:getEntityPerson - exception msg: " + re.getMessage());
			throw new GamelogException(re);
		}
	}

    

}
