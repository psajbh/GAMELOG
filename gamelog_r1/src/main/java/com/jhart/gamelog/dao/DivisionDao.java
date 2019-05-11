package com.jhart.gamelog.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class DivisionDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(DivisionDao.class);
    
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        return null;
    }


}
