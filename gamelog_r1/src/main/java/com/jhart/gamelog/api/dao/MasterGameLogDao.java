package com.jhart.gamelog.api.dao;

import java.util.List;

import com.jhart.gamelog.entities.gamelog.EntityGamelog;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public interface MasterGameLogDao {
	
    List<EntityGamelog> getPersistedGamelogs();
    void saveAll(List<Gamelog> modelGamelog) throws NullSessionException, DbExecutionException, Exception;
    void saveAll(List<Gamelog> modelGamelog, Integer i) throws NullSessionException, DbExecutionException, Exception;
    
}
