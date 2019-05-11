package com.jhart.gamelog.utils.dbconfig;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.utils.dbconfig.exception.DbConfigException;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;
import com.jhart.gamelog.utils.dbconfig.exception.UnsupportedHostException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DbUtil {
	private static final Logger LOG = LoggerFactory.getLogger(DbUtil.class);
	private static final String DEFAULT_CFG_XML = "hibernate.cfg.xml";
	private static final String COMPUTERNAME = "COMPUTERNAME";
	private static final String HOSTNAME = "HOSTNAME";

	private static StandardServiceRegistry registry = null;
	private static Properties dbProps = null;
	private static SessionFactory sessionFactory = null;
	
	
	public static void setup(String propFileName) throws DbConfigException{
	    LOG.debug("DbUtil:setup - starting db setup with propFileName: " + propFileName);
	    String errorMessage = null;
		try {
			LOG.debug("DbUtil:setup - calling propertSetup with propFileName: " + propFileName);
			dbProps = propertySetup(propFileName);
			LOG.debug("DbUtil:setup - calling configSetup");
			configSetup();
			LOG.debug("DbUtil:setup - complete");
			
		}
		catch (UnsupportedHostException unSupportedHostException) {
		    errorMessage = unSupportedHostException.getMessage();
		    LOG.error(errorMessage,unSupportedHostException);
		}
		catch(NullSessionException nullSessionException) {
		    errorMessage = nullSessionException.getMessage();
			LOG.error(errorMessage,nullSessionException);
		}
	    catch(Exception e) {
	        errorMessage = e.getMessage();
	        LOG.error(errorMessage, e);
	    }
		
		if (null != errorMessage) {
		    DbConfigException dbConfigException = new DbConfigException();
		    dbConfigException.setMessage("Failure to config database: " + errorMessage);
		    LOG.debug("starting db setup failure");
		}
		else {
		    LOG.debug("starting db setup satisfactorly setup");
		}
	}
	
    public static void shutdown() {
        LOG.debug("shutting down database");
        
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
         }
        
        if (null != sessionFactory) {
        	sessionFactory.close();
        }
    }
	
    private static Properties propertySetup(String propertyFile) throws Exception{
    	LOG.debug("inside propertySetup");
    	String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    	String dbConfigPath = rootPath + propertyFile;
    	Properties dbProps = new Properties();
    	
 	   	try {
		   dbProps.load(new FileInputStream(dbConfigPath));
		   return dbProps;
	   }
	   catch(IOException ioe) {
		   System.out.println("failure to create property file - app is a nogo");
		   throw new Exception("Failure Loading Properties");
	   }
    }
    
    private static void configSetup() throws Exception{
        LOG.debug("DbUtil:configSetup - start");
    	HostInfo hostInfo = getHostInfo();
    	DbConfig dbConfig = setDBParamsWithProps(hostInfo);
    	DbUtil.setSessionFactory(dbConfig);
    	LOG.debug("successfully configured ORM");
    }
	
    //https://docs.jboss.org/hibernate/orm/3.2/reference/en/html/session-configuration.html
	private static void setSessionFactory(DbConfig dbConfig) {
		LOG.debug("inside setSessionFactory");
		sessionFactory = null;
		Configuration cfg = new Configuration()
			.setProperty("hibernate.connection.driver_class",dbConfig.getDriver())
			.setProperty("hibernate.connection.url", dbConfig.getUrl())
			.setProperty("hibernate.connection.username", dbConfig.getUser())
			.setProperty("hibernate.connection.password", dbConfig.getPassword())
			.setProperty("hibernate.dialect", dbConfig.getDialect())
			.setProperty("hibernate.show_sql", "false")
			.setProperty("hibernate.format_sql", "true")
			//.addAnnotatedClass(com.jhart.gamelog.entities.park.EntityPark.class)
			.configure();
		
		try {
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
			registryBuilder.applySettings(cfg.getProperties());
			
			StandardServiceRegistry standardRegistry = registryBuilder.configure(DbUtil.DEFAULT_CFG_XML).build();
			//StandardServiceRegistry standardRegistry = registryBuilder.configure().build();
			Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			sessionFactory = metaData.getSessionFactoryBuilder().build();
			SessionProvider.setSessionFactory(sessionFactory);
			
		} 
		catch (Throwable th) {
			System.err.println("Enitial SessionFactory creation failed" + th);
			throw new ExceptionInInitializerError(th);
		}
	}
	
	
	public static HostInfo getHostInfo() {
		LOG.debug("DbUtil:getHostInfo - start");
		Map<String, String> env = System.getenv();
   	   	LOG.debug("DbUtil:getHostInfo - env = " + env);
   	   
   	   	if (env.containsKey(COMPUTERNAME)) {
   	   		String envName = env.get(DbUtil.COMPUTERNAME);
   	   		LOG.debug("HostInfo:getHostInfo - returning envName: " + envName);
   	   		return getHostInfo(envName); 
   	   	}
   	   	else if (env.containsKey(HOSTNAME)) {
   	   		String envName =  env.get(DbUtil.HOSTNAME);
   	   		LOG.debug("HostInfo:getHostInfo - returning envName: " + envName);
   	   		return getHostInfo(envName);
   	   	}
   	   	else {
   	   		LOG.debug("HostInfo:getHostInfo - returning envName: UNDETERMINED");
   	   		return HostInfo.UNDETERMINED;
   	   	}
   	   
      }
      
      private static HostInfo getHostInfo(String envName) {
    	  	LOG.debug("DbUtil:getHostInfo - envName: " + envName);
   	   		String reformattedName = envName.replaceAll("-", "_");
   	   		return HostInfo.valueOf(reformattedName);
      }
      
      private static DbConfig setDBParamsWithProps(HostInfo hostInfo) throws UnsupportedHostException{
          LOG.debug("running setDBParamsWithProps for computerName: " + hostInfo.getName());
          
          if(null == dbProps) {
              throw new UnsupportedHostException(new Exception("dbProps generation failure"));
          }
          
          DbConfig dbConfig = new DbConfig();
          dbConfig.setDriver((String) dbProps.get(hostInfo.getCode() + SqlPropType.DRIVER.getValue()));
          dbConfig.setUrl((String) dbProps.get(hostInfo.getCode() + SqlPropType.URL.getValue()));
          dbConfig.setUser((String) dbProps.get(hostInfo.getCode() + SqlPropType.USER.getValue()));
          dbConfig.setPassword(deCrypt(hostInfo, SqlPropType.PASS.getValue()));
          dbConfig.setDialect((String) dbProps.get(hostInfo.getCode() + SqlPropType.DIALECT.getValue()));
          return dbConfig;
          
      }

      private static String deCrypt(HostInfo hostInfo, String prop) {
          Decryptor decryptor = new Decryptor();
          return decryptor.decrypt((String) dbProps.get(hostInfo.getCode() + prop));
      }

}
