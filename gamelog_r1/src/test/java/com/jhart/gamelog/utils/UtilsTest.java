package com.jhart.gamelog.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsTest {
    private static final Logger LOG = LoggerFactory.getLogger(UtilsTest.class);
    
    @Test
    public void testGetFormattedLocalDate_yyyyMMDD() throws Exception {
      String date = "20170402";
      String pattern = Utils.YRMODA_SANFORMAT;
      LocalDate localDate = null;
      
      try {
          localDate = Utils.getFormattedLocalDate(date, pattern);
      }
      catch(Exception e) {
          LOG.error("UtilsTest:testGetFormattedLocalDate_yyyyMMDD  - exception: " + e.getMessage());
      }
      
      Assert.assertNotNull(localDate);
    }
    
    @Test
    public void testConvertMinutesToHoursPlus() throws Exception{
    	int time = 226;
    	int hours = time / 60; //since both are ints, you get an int
    	int minutes = time % 60;
    	System.out.printf("%d:%02d", hours, minutes);
    }
    
    @Test
    public void testVirusProtection() throws Exception {
    	
    	 Runtime rt = Runtime.getRuntime();
         Process pr = null;
         try {
             pr = rt.exec("cmd echo test");
             System.out.println("Exited with code " + pr.waitFor());
             System.out.println();
         }
         catch (Exception e) {
             System.out.println("Error Executing Programs");
         }
    }

}

/*
//commands[0] = "cmd";
//commands[1] = "/c";
//commands[2] = "C:\\Program Files\\AVG\\AVG10\\avgscanx.exe";
//commands[3] = "/scan=" + filename;
//commands[4] = "/report=" + virusoutput;

//String[] commands = new String[4];
//commands[0] = "echo";
//commands[1] = "test";
//Process p = Runtime.getRuntime().exec("notepad.exe test.txt");      
//System.out.println("Waiting for notepad to exit...");
//System.out.println("Exited with code " + p.waitFor());    	
try {
	Runtime runtime = Runtime.getRuntime();
	Process process = runtime.exec("cmd echo bullshit");
	System.out.println("Waiting for notepad to exit...");
	System.out.println("Exited with code " + process.waitFor());
	//process.wait();
}
catch(Exception e) {
	System.out.println(e.getMessage());
}
*/