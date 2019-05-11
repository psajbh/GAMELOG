package com.jhart.gamelog.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.gamelog.EntityTeamGamelog;
import com.jhart.gamelog.entities.gamelog.EntityUmpireGamelog;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class UmpireDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(UmpireDao.class);
    
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        return null;
    }
    
    public void save(Session session, EntityUmpireGamelog entityUmpireGamelog) {
        
        try {
            session.saveOrUpdate(entityUmpireGamelog);
            LOG.debug("successfully saved entityUmpireGamelog id: " + entityUmpireGamelog.getUmpireGameLogId());
        }
        catch(Exception e) {
            LOG.debug("error executing save entityUmpireGamelog msg: " + e.getMessage());
        }
        
    }



}
