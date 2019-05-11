package com.jhart.gamelog.utils.appconfig;

public class AppConfig {
	private static String parseDataSourcePath;
	private static Integer maxGameLogLimiter;
	private static String control;
	
	private static AppConfig instance;
	
	private AppConfig(){} 
	
	public static synchronized AppConfig getInstance(){
		if(instance == null){
            instance = new AppConfig();
        }
        return instance;
    }

	public static String getParseDataSourcePath() {
		return parseDataSourcePath;
	}

	public static void setParseDataSourcePath(String parseDataSourcePath) {
		AppConfig.parseDataSourcePath = parseDataSourcePath;
	}

	public static Integer getMaxGameLogLimiter() {
		return maxGameLogLimiter;
	}

	public static void setMaxGameLogLimiter(Integer maxGameLogLimiter) {
		AppConfig.maxGameLogLimiter = maxGameLogLimiter;
	}

	public static String getControl() {
		return control;
	}

	public static void setControl(String control) {
		AppConfig.control = control;
	}

}
