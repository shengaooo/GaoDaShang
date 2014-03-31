package database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TableManager {
	protected SessionFactory mySessionFactory;
	
	public TableManager() {
		try {
		    Configuration configuration = new Configuration().configure();   
		    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();  
	        mySessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch (Exception ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
	        throw new ExceptionInInitializerError(ex); 
		}
	}
}
