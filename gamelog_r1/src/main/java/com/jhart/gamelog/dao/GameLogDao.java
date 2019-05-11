package com.jhart.gamelog.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.utils.dbconfig.SessionProvider;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;
import com.jhart.gamelog.api.dao.GameLogDaoIntf;

public abstract class GameLogDao implements GameLogDaoIntf{
	private static final Logger LOG = LoggerFactory.getLogger(GameLogDao.class);
	
	@Override
	public Session getSession() throws NullSessionException{
        Session session = SessionProvider.getSession();
        if (null == session) {
            LOG.error("session is null");
            throw new NullSessionException("failed to get session from SessionProvider");
        }
	    return session;
	}
	
	
	
	
	

}
