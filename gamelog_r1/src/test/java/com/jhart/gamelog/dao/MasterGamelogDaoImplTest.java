package com.jhart.gamelog.dao;

import org.junit.Assert;
import org.junit.Test;

public class MasterGamelogDaoImplTest {
    
    /**
     *  test to insure comparison between Object and primative values
     *  will can be determined.               
     *  if (count > testLimiter) {break;}
     */
    @Test
    public void testIntegerToInt() {
       
        int i = 1;
        Integer integer = 2;
        
        Assert.assertTrue(i < integer);
        Assert.assertFalse(i > integer);
        
    }

}
