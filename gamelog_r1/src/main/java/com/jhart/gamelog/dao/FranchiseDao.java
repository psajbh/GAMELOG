package com.jhart.gamelog.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.team.EntityFranchise;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class FranchiseDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(FranchiseDao.class);
    
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public EntityFranchise getEntityFranchise(Session session, String code) throws GamelogException, NullSessionException{
        LOG.debug("FranchiseDao:getEnityFranchise - code: " + code);
        Query<EntityFranchise> q = session.getNamedQuery("get_franchise_by_code");
        q.setParameter("code", code);
        EntityFranchise entityFranchise = (EntityFranchise) q.uniqueResult();  // won't throw exception like getSingleResult
        if (null != entityFranchise) {
            return entityFranchise;
        }
        else {
            return createNewFranchise(session, code);
        }
    }
    
    public EntityFranchise createNewFranchise(Session session, String code) {
        LOG.trace("FranchiseDao:createNewFranchise - start");
        EntityFranchise newFranchise = new EntityFranchise();
        newFranchise.setCode(code);
        Integer id = (Integer) session.save(newFranchise);
        LOG.debug("created newLocation with id: " + id);
        return newFranchise;

        
    }

}
