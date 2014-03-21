package database;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DBTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestaurantInformation sameplRest = new RestaurantInformation(1.0, 1.0, "Name", "Address", "Tel1", "Tel2", "Mgr", "Mgrtel");
		
		Configuration configuration = new Configuration().configure();   
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();  
	    SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);  

	    Session ss = sf.openSession();
        ss.beginTransaction();
        ss.save(sameplRest);
        ss.getTransaction().commit(); 
 
        ss.close();
        sf.close();

	}

}
