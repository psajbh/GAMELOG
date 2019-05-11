
package com.jhart.gamelog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.service.Service;
import com.jhart.gamelog.dao.MasterGamelogDaoImp;
import com.jhart.gamelog.entities.gamelog.EntityGamelog;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.utils.Utils;
import com.jhart.gamelog.utils.appconfig.AppConfig;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GamelogService implements Service<Gamelog>{
    private static final Logger LOG = LoggerFactory.getLogger(GamelogService.class);
    private MasterGamelogDaoImp masterGamelogDao = new MasterGamelogDaoImp();
    private Integer recordLimiter = null;
    
     
    @Override
    public boolean persist(List<Gamelog> gamelogList) {
        LOG.debug("persist: - potentially " + ((gamelogList == null) ? 0 : gamelogList.size()) + " records");
        recordLimiter = AppConfig.getMaxGameLogLimiter();
        
        boolean rValue = false;
        List<Gamelog> saveList = new ArrayList<>();
        
        Map<String, EntityGamelog> persistedGameLogs =  getPersistedGameLogs();
        LOG.debug("persist: - number of persisted gamelogs: " + persistedGameLogs.size());
        
        if (persistedGameLogs.size() > 0) {
        	LOG.debug("persist: adding selected number of gamelogs for persistence");
            for (Gamelog gamelog : gamelogList) {
                String gamelogcode = Utils.getGamelogCode(gamelog.getSeason(), gamelog.getGameLogId());
                if (persistedGameLogs.get(gamelogcode) == null){
                    saveList.add(gamelog);
                }
            }
        }
        else {
        	LOG.debug("persist: add all gamelogs for persistence");
            saveList.addAll(gamelogList);
        }

        LOG.debug("persist: - saveList size: " + saveList.size());
        if (saveList.size() > 0) {
            try {
            	LOG.debug("persist: - calling saveAll gamelogs for size: " + saveList.size() + " record limiter: " + recordLimiter);
            	if (null != recordLimiter & recordLimiter  > 0)
            		masterGamelogDao.saveAll(saveList, recordLimiter);
            	else {
            		masterGamelogDao.saveAll(saveList);
            	}
                rValue = true;
            }
            catch(NullSessionException nse) {
                LOG.error("failed to establish a hibernate session", nse);
            }
            catch(DbExecutionException dbe) {
                LOG.error("failed to execute db functionality: " + dbe.getMessage()  );
            }
            catch(Exception e) {
            	LOG.error("failed to execute db functionality: " + e.getMessage()  );
            }
        }
        LOG.debug("GamelogService : persist - finished with rValue: " + rValue);
        return rValue;
    }

    private Map<String, EntityGamelog> getPersistedGameLogs(){
        Map<String, EntityGamelog> persistedGameLogs = new HashMap<>();
        for(EntityGamelog entityGamelog : masterGamelogDao.getPersistedGamelogs()) {
            persistedGameLogs.put(entityGamelog.getGameCode(), entityGamelog);
        }
        return persistedGameLogs;
    }

    
}
