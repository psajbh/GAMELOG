package com.jhart.gamelog.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.team.EntityFranchise;
import com.jhart.gamelog.entities.team.EntityLeague;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class LeagueDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(LeagueDao.class);

    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public EntityLeague getEntityLeagueWithDivision(Session session, String leagueCode, String divisionCode) throws GamelogException, NullSessionException{
        LOG.debug("LeagueDao:getEntityLeagueWithDivision - leagueCode: " + leagueCode + " divisionCode: " + divisionCode);
        Query<EntityLeague> q  = session.getNamedQuery("get_league_by_lgcode_divcode");
        q.setParameter("lgCode", leagueCode);
        q.setParameter("divCode", divisionCode);

        EntityLeague entityLeague = (EntityLeague) q.uniqueResult();  // won't throw exception like getSingleResult
        if (null != entityLeague) {
            return entityLeague;
        }
        else {
            LOG.error("returning a null league, wtf");
            return null;  // this should not happen
        }
    }
    
    @SuppressWarnings("unchecked")
    public EntityLeague getEntityLeagueWithOutDivision(Session session, String leagueCode) throws GamelogException, NullSessionException{
        LOG.debug("LeagueDao:getEntityLeagueWithDivision - leagueCode: " + leagueCode);
        Query<EntityLeague> q  = session.getNamedQuery("get_league_by_lgcode_null_divcode");
        q.setParameter("lgCode", leagueCode);

        List<EntityLeague> entityLeagues = (List<EntityLeague>) q.list();  // won't throw exception like getSingleResult
        
        EntityLeague league = null;
        for(EntityLeague l : entityLeagues) {
            if (null == l.getDivision()) {
                league = l;
            }
        }
        return league;
    }
    

}
