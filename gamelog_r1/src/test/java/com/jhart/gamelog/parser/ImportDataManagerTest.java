package com.jhart.gamelog.parser;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.exception.GamelogException;

public class ImportDataManagerTest {
    private static final Logger LOG = LoggerFactory.getLogger(ImportDataManagerTest.class);
    
    @Test
    public void TestConstructor() {
        ImportDataManager dataManager = new ImportDataManager("2017");
        Assert.assertNotNull(dataManager);
        
        
        
        LOG.debug("successfully constructed a ImportDataManager object");
        
    }
    
    @Test(expected = GamelogException.class)
    public void TestConstructor2(){
        ImportDataManager dataManager = new ImportDataManager("hello");
    }

}
