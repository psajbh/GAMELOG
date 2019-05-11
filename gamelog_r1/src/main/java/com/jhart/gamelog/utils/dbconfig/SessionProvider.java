package com.jhart.gamelog.utils.dbconfig;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionProvider {
	
	private SessionProvider() {}

	private static SessionFactory masterSessionFactory;
	
	public static synchronized Session getSession() {
		return masterSessionFactory.openSession();
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		masterSessionFactory = sessionFactory;
	}
	
	

}
