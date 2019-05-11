package com.jhart.gamelog.utils.appconfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.jhart.gamelog.utils.dbconfig.DbUtil;
import com.jhart.gamelog.utils.dbconfig.HostInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppUtil {
	private static final String GAMELOG_LIMITER = "GAMELOG_LIMITER";
	private static final String DEFAULT_CONTROL = "DEFAULT_CONTROL";
	private static Properties configProps = null;

	public static AppConfig setup(String propFileName) {
		log.trace("setup: propFileName: " + propFileName);
		AppConfig appConfig = AppConfig.getInstance();
		
		try {
			configProps = propertySetup(propFileName);
			HostInfo hostInfo = DbUtil.getHostInfo();
			AppConfig.setParseDataSourcePath((String) configProps.get(hostInfo.getCode() + AppPropType.DATAFILE_LOCATION.getValue()));
			AppConfig.setMaxGameLogLimiter(Integer.valueOf((String) configProps.get(GAMELOG_LIMITER)));
			AppConfig.setControl((String) configProps.get(DEFAULT_CONTROL));
		}
		catch(Exception e) {
			log.error("Error generating AppConfig file");
			
		}
		return appConfig;
	}
		
 	private static Properties propertySetup(String propFileName) throws Exception{
		log.debug("AppUtil:propertySetup - start propFileName: " + propFileName);
	    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	    String dbConfigPath = rootPath + propFileName;
	    log.debug("AppUtil:propertySetup - dbConfigPath: " + dbConfigPath);
	    
	    Properties configProps = new Properties();
	    	
	 	try {
	 	   	configProps.load(new FileInputStream(dbConfigPath));
			return configProps;
		}
		catch(IOException ioe) {
			   System.out.println("failure to create property file - app is a nogo");
			   throw new Exception("Failure Loading Properties");
		}
		
	}
	
}
