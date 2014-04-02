package database;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session; 
import org.hibernate.Transaction;

import database.RestaurantInformation;


public class RestaurantInformationManager extends TableManager {
	public Integer AddRestaurantInformation(Double restaurantLatitude, Double restaurantLongitude, String restaurantName, String restaurantAddress, String contactNumber1, String contactNumber2, String managerName, String managerContact ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		Integer primaryKey = null;
		
		try {
			tx = session.beginTransaction();
			RestaurantInformation myTuple = new RestaurantInformation(restaurantLatitude, restaurantLongitude, restaurantName, restaurantAddress, contactNumber1, contactNumber2, managerName, managerContact );
			primaryKey = (Integer) session.save(myTuple);
			tx.commit();
			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}		
		return primaryKey;		
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<RestaurantInformation> ListAllRestaurantInformation() {
		Iterator<RestaurantInformation> myIterator = null;
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			myIterator = (Iterator<RestaurantInformation>)(session.createQuery("FROM RestaurantInformation").list().iterator());
			tx.commit();			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}
		return myIterator;
	}
	
	public void DeleteRestaurantInformation(Integer primaryKey) {
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			RestaurantInformation tupleToBeDeleted = (RestaurantInformation)session.get(RestaurantInformation.class, primaryKey);
			session.delete(tupleToBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdateRestaurantInformation(Integer primaryKey, Double restaurantLatitude, Double restaurantLongitude, String restaurantName, String restaurantAddress, String contactNumber1, String contactNumber2, String managerName, String managerContact ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			RestaurantInformation tupleToBeUpdated = (RestaurantInformation)session.get(RestaurantInformation.class, primaryKey);
			
			tupleToBeUpdated.setRestaurantLatitude(restaurantLatitude);
			tupleToBeUpdated.setRestaurantLongitude(restaurantLongitude);
			tupleToBeUpdated.setRestaurantName(restaurantName);
			tupleToBeUpdated.setRestaurantAddress(restaurantAddress);
			tupleToBeUpdated.setContactNumber1(contactNumber1);
			tupleToBeUpdated.setContactNumber2(contactNumber2);
			tupleToBeUpdated.setManagerName(managerName);
			tupleToBeUpdated.setManagerContact(managerContact);
		
			session.update(tupleToBeUpdated);
			tx.commit();
		
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}		
	}	
}
