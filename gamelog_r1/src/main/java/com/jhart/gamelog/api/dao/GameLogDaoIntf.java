package com.jhart.gamelog.api.dao;

import java.util.List;

import org.hibernate.Session;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public interface GameLogDaoIntf {
    
    Session getSession() throws NullSessionException;
    List<IModel> getAll() throws NullSessionException, DbExecutionException;
    

}
