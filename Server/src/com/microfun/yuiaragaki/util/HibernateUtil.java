package com.microfun.yuiaragaki.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * hibernateπ§æﬂ¿‡
 * @author kevinchen
 *
 */
public class HibernateUtil {

	private static Configuration configuration = null;
	private static SessionFactory sessionFactory = null;
	
	static{
		System.out.println("static run");
		configuration = new Configuration();
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory();
	}
	
	public static Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
	
}
