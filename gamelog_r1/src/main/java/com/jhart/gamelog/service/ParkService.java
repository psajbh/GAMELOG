package com.jhart.gamelog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.dao.ParkDao;
import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.model.parks.Park;
import com.jhart.gamelog.utils.dbconfig.DbUtil;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParkService {
    private static final Logger LOG = LoggerFactory.getLogger(ParkService.class);

//    public ParkService() {
//        // TODO Auto-generated constructor stub
//    }
    
    public boolean persist(List<Park> parkList) {
        LOG.debug("inside persistParkRep with potentially " + ((parkList == null) ? 0 : parkList.size()) + " records");
        boolean rValue = false;
        Map<String, EntityPark> persistedParks = new HashMap<>();
        List<Park> saveList = new ArrayList<>();
        
        ParkDao gle = new ParkDao();
        for(EntityPark entityPark : gle.getPersistedParks()) {
            persistedParks.put(entityPark.getCode(),entityPark);
        }
        
        LOG.debug("number of persisted parks: " + persistedParks.size());
        
        if (persistedParks.size() > 0) {
            for ( Park park : parkList) {
                if (persistedParks.get(park.getParkId()) == null) {
                    saveList.add(park);
                }
            }
        }
        else {
            saveList.addAll(parkList);
        }
        
        if(saveList.size() > 0) {
            try {
                gle.saveAll(saveList);
                rValue = true;
            }
            catch(NullSessionException nse) {
                LOG.error("failed to establish a hibernate session", nse);
            }
            catch(DbExecutionException dbe) {
                LOG.error("failed to execute db functionality: " + dbe.getMessage()  );
            }
        }
        else {
            LOG.debug("NO new parks added to database");
            rValue = true;
        }
        
        return rValue;
        
        //DbUtil.shutdown();
        //LOG.info("shutdown database connection");
        //return false;
    }

}
