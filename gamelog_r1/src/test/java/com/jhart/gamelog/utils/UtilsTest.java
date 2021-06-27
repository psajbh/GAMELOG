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
    public void gamelogLineTest() {
           String test = "\"20200723\",\"0\"";
           String[] splits = test.split(",");
           System.out.println("splits: " + splits);
           
        //String x = ""20200723","0","Thu","SFN","NL",1,"LAN","NL",1,1,8,51,"N","","","","LOS03",,176,"001000000","00010052x",32,8,0,0,0,1,0,1,0,0,0,8,0,0,1,0,5,6,8,8,0,0,24,12,1,0,1,0,37,12,4,0,1,8,0,0,1,5,0,6,0,0,1,0,11,5,1,1,0,0,27,11,1,0,2,0,"millb901","Bill Miller","mosce901","Edwin Moscoso","eddid901","Doug Eddings","drakr901","Rob Drake","","(none)","","(none)","kaplg001","Gabe Kapler","robed001","Dave Roberts","kolaa001","Adam Kolarek","roget002","Tyler Rogers","","(none)","turnj001","Justin Turner","cuetj001","Johnny Cueto","may-d003","Dustin May","yastm001","Mike Yastrzemski",8,"florw001","Wilmer Flores",5,"sandp001","Pablo Sandoval",3,"dicka001","Alex Dickerson",7,"pench001","Hunter Pence",10,"mccaj002","Joe McCarthy",9,"dubom001","Mauricio Dubon",4,"crawb001","Brandon Crawford",6,"heint001","Tyler Heineman",2,"muncm001","Max Muncy",3,"bettm001","Mookie Betts",9,"bellc002","Cody Bellinger",8,"turnj001","Justin Turner",5,"seagc001","Corey Seager",6,"herne001","Enrique Hernandez",4,"pedej001","Joc Pederson",7,"polla001","A.J. Pollock",10,"barna001","Austin Barnes",2,"","Y"";
    }
    
    
    //@Test
/*    public void testVirusProtection() throws Exception {
    	
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
*/
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