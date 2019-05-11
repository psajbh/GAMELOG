package com.jhart.gamelog.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.team.EntitySeason;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class SeasonDao extends GameLogDao{
	private static final Logger LOG = LoggerFactory.getLogger(SeasonDao.class);
	
	@SuppressWarnings("unchecked")
    public EntitySeason getEntitySeason(Session session, String code) throws GamelogException, NullSessionException{
	    LOG.debug("SeasonDao:getEnitySeason - code: " + code);
		try {
			Query<EntitySeason> qSeason = session.getNamedQuery("get_season_from_code");
			qSeason.setParameter("code", code);
			EntitySeason entitySeason = (EntitySeason) qSeason.uniqueResult();
			if (null != entitySeason) {
			    LOG.debug("SeasonDao:getEnitySeason - returning uniqueResult with id: " + entitySeason.getSeasonId());
				return entitySeason;
			}
			else {
			    LOG.debug("SeasonDao:getEnitySeason - creating a new EntitySeason ");
				EntitySeason newEntitySeason = new EntitySeason();
				newEntitySeason.setCode(code);
				Integer id = null;
				try {
				    id = (Integer) session.save(newEntitySeason);
				}
				catch(Exception e) {
				    throw e;
				}
				//newEntitySeason.setSeasonId(id);
				return newEntitySeason;
			}
		}
		catch(RuntimeException re) {
			LOG.error("SeasonDao:getEntitySeason - exception msg: " + re.getMessage());
			throw new GamelogException(re);
		}
	}
	
	 

	@Override
	public List<IModel> getAll() throws NullSessionException, DbExecutionException{
		return null;
	}
	
}
