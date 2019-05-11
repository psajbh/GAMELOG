package com.jhart.gamelog.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.model.parks.Park;
import com.jhart.gamelog.transform.ParkTransformer;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class ParkDao extends GameLogDao {
    private static final Logger LOG = LoggerFactory.getLogger(ParkDao.class);
    //TODO: autowire this
    private ParkTransformer transformer = new ParkTransformer();

    public ParkDao() {}
    
    /**
     * getAll() returns a list of Park that are persisted.
     * return List<IModel> 
     */
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        LOG.debug("getAll() - get all park entities and match against parsed model");
        List<IModel> parks = new ArrayList<>();
        
        List<EntityPark> entityParks = getAllEntityParks();
        LOG.debug("getAll() - translate existing entity parks to a parsed park");
        if (entityParks.size() > 0) {
            for(EntityPark dp: entityParks) {
            	// need to test transformer.
                Park park = (Park)transformer.transform(dp);
                
                parks.add((IModel)park);
            }
        }
        LOG.debug("returning list of Park objects that are persisted: " + parks.size());
        return parks;
    }
    
    public void saveAll(List<Park> modelParks) throws NullSessionException, DbExecutionException{
    	// transaction start here.
    	LOG.debug("ParkDao : saveAll - modelPark size: " + modelParks.size());
    	Session session = getSession();
    	session.beginTransaction();
    	try {
    		for(Park park : modelParks) {
    			EntityPark entityPark = (EntityPark)transformer.transform(park);
    			save(entityPark,session);
    		}
    		session.getTransaction().commit();
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
    
    public List<EntityPark> getPersistedParks() {
    	LOG.debug("ParkDAO : getPersistedParks - start");
        List<EntityPark> persistedParks = new ArrayList<>();
        try {
            persistedParks = getAllEntityParks();
        }
        catch(Exception e) {
            LOG.debug("getPersistedParks: " + e.getMessage());
        }
        return persistedParks;
    }
    
    
    @SuppressWarnings({ "unchecked" })
	private List<EntityPark> getAllEntityParks() throws NullSessionException, DbExecutionException{
    	LOG.debug("getAllEntityParks() - query for all persisted entity parks");
        List<EntityPark> entityParks;
        
        Session session = getSession();
        session.beginTransaction();
        @SuppressWarnings("rawtypes")
		Query query= session.createQuery("from EntityPark");
        entityParks = query.list();
        session.getTransaction().commit();
        session.close();
        
        return entityParks;
    }
    
    @SuppressWarnings({ "unchecked", "unused" })
	private void save(EntityPark entityPark, Session session) throws NullSessionException, DbExecutionException{
        
        try {
            EntityLocation location = entityPark.getLocation();
            EntityLocation entityLocation = null;
            
            Query<EntityLocation> query = session.getNamedQuery("get_location_by_state_city");
            query.setParameter("stateCode", location.getStateCode());
            query.setParameter("city", location.getCity());
            //entityLocation = (EntityLocation) query.getSingleResult();
            entityLocation = (EntityLocation) query.uniqueResult();  // won't throw exception like getSingleResult
            
            if (null != entityLocation) {
            	entityPark.setLocation(entityLocation);
            	LOG.debug("saved entityPark with existing entityLocation: " + entityLocation.getStateCode() + " " + entityLocation.getCity());
            }
            else {
            	Integer id = (Integer) session.save(location);
            	entityPark.setLocation(location);
            	LOG.debug("saved new entityLocation: " + location.getStateCode() + " " + location.getCity());
            }
            session.saveOrUpdate(entityPark);
            //session.getTransaction().commit();
            //session.close();
        }
        catch(Exception sqle) {
            LOG.error("save error - " + sqle.getMessage());
            throw new DbExecutionException(sqle);
        }
    }
    
    @SuppressWarnings("unchecked")
	public EntityPark getEntityPark(Session session, String code) throws GamelogException, NullSessionException{
    	if (null == code) {
    		System.out.println("STOP");
    	}
		try {
			Query<EntityPark> qPark = session.getNamedQuery("get_park_from_code");
			qPark.setParameter("code", code);
			EntityPark entityPark = (EntityPark) qPark.uniqueResult();
			if (null != entityPark) {
				return entityPark;
			}
			else {
				EntityPark newEntityPark = new EntityPark();
				newEntityPark.setCode(code);
				Integer id = (Integer) session.save(newEntityPark);
				newEntityPark.setParkId(id);
				return newEntityPark;
			}
		}
		catch(RuntimeException re) {
			LOG.error("ParkDao:getEntityPark - park code: "+code+" exception msg: " + re.getMessage());
			throw new GamelogException(re);
		}
	}
	
    


}
