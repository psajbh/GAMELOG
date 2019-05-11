package com.jhart.gamelog.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class LocationDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(LocationDao.class);
    
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public EntityLocation getEntityLocation(Session session, String state, String city) throws NullSessionException, Exception{
        Query<EntityLocation> q = (Query<EntityLocation>)session.getNamedQuery("get_location_by_state_city");
        q.setParameter("stateCode", state);
        q.setParameter("city", city);
        EntityLocation entityLocation = (EntityLocation) q.uniqueResult();  // won't throw exception like getSingleResult
        if (null != entityLocation) {
            return entityLocation;
        }
        else {
            return createNewLocation(session, state, city);
        }
    }
    
    private EntityLocation createNewLocation(Session session, String state, String city) {
        LOG.trace("LocationDao:createNewLocation - start");
        EntityLocation newLocation = new EntityLocation();
        newLocation.setState(state);
        newLocation.setCity(city);
        Integer id = (Integer) session.save(newLocation);
        LOG.debug("created newLocation with id: " + id);
        return newLocation;
    }

}
