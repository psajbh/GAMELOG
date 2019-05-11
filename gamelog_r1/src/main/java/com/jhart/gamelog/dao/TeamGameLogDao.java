package com.jhart.gamelog.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.gamelog.EntityGameLineBat;
import com.jhart.gamelog.entities.gamelog.EntityGameLineDefense;
import com.jhart.gamelog.entities.gamelog.EntityGameLinePitch;
import com.jhart.gamelog.entities.gamelog.EntityLineItemOrder;
import com.jhart.gamelog.entities.gamelog.EntityLineItemPos;
import com.jhart.gamelog.entities.gamelog.EntityTeamGamelog;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class TeamGameLogDao extends GameLogDao {
	private static final Logger LOG = LoggerFactory.getLogger(TeamGameLogDao.class);

	@Override
	public List<IModel> getAll() throws NullSessionException, DbExecutionException {
		return null;
	}

	public void save(Session session, EntityTeamGamelog entityTeamGamelog) {
		LOG.debug("** saving entityTeamGamelog");
		try {
			session.saveOrUpdate(entityTeamGamelog);
			LOG.debug("successfully saved teamgamelog id: " + entityTeamGamelog.getTmGamelogId());
		} 
		catch (Exception e) {
			LOG.debug("error executing save teamentityGamelog msg: " + e.getMessage());
		}

	}

	public void save(Session session, EntityGameLineBat entityGameLineBat) throws Exception {
		LOG.debug("** saving entityGameLineBat");
		try {
			session.saveOrUpdate(entityGameLineBat);
			LOG.debug("successfully saved entityGameLineBat id: " + entityGameLineBat.getTmGamelineBatId());
		} 
		catch (Exception e) {
			LOG.debug("error executing save entityGameLineBat msg: " + e.getMessage());
			throw e;
		}

	}

	public void save(Session session, EntityGameLinePitch entityGameLinePitch) throws Exception {
		LOG.debug("** saving entityGameLinePitch");
		try {
			session.saveOrUpdate(entityGameLinePitch);
			LOG.debug("successfully saved entityGameLinePitch id: " + entityGameLinePitch.getTmGamelinePitchId());
		}
		catch (Exception e) {
			LOG.debug("error executing save entityGameLinePitch msg: " + e.getMessage());
			throw e;
		}

	}

	public void save(Session session, EntityGameLineDefense entityGameLineDefense) throws Exception {
		LOG.debug("** saving entityGameLineDefense");
		try {
			session.saveOrUpdate(entityGameLineDefense);
			LOG.debug("successfully saved entityGameLineDefense id: " + entityGameLineDefense.getTmGamelineDefenseId());
		} 
		catch (Exception e) {
			LOG.debug("error executing save entityGameLineDefense msg: " + e.getMessage());
			throw e;
		}
	}

	public void save(Session session, EntityLineItemOrder entityLineItemOrder) throws Exception {
		LOG.debug("** saving entityLineItemOrder");
		try {
			session.saveOrUpdate(entityLineItemOrder);
			LOG.debug("successfully saved entityLineItemOrder id: " + entityLineItemOrder.getLineitemOrderId());
		}
		catch (Exception e) {
			LOG.debug("error executing save entityLineItemOrder msg: " + e.getMessage());
			throw e;
		}
	}

	public void save(Session session, EntityLineItemPos entityLineItemPos) throws Exception {
		LOG.debug("** saving entityLineItemPos");
		try {
			session.saveOrUpdate(entityLineItemPos);
			LOG.debug("successfully saved entityLineItemPos id: " + entityLineItemPos.getLineitemPosId());
		} 
		catch (Exception e) {
			LOG.debug("error executing save entityLineItemPos msg: " + e.getMessage());
			throw e;

		}

	}

}
