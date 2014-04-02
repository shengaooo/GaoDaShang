package database;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session; 
import org.hibernate.Transaction;

import database.User;


public class UserManager extends TableManager {
	public Integer AddUser(String userAddress, String userName, String mobileNumber, Integer userGrade, Integer jiFen, Double userPaid ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		Integer primaryKey = null;
		
		try {
			tx = session.beginTransaction();
			User myTuple = new User(userAddress, userName, mobileNumber, userGrade, jiFen, userPaid);
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
	public Iterator<User> ListAllUser() {
		Iterator<User> myIterator = null;
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			myIterator = (Iterator<User>)(session.createQuery("FROM User").list().iterator());
			tx.commit();			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}
		return myIterator;
	}
	
	public void DeleteUser(Integer primaryKey) {
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			User tupleToBeDeleted = (User)session.get(User.class, primaryKey);
			session.delete(tupleToBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdateUser(Integer primaryKey, String userAddress, String userName, String mobileNumber, Integer userGrade, Integer jiFen, Double userPaid ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			User tupleToBeUpdated = (User)session.get(User.class, primaryKey);
			
			tupleToBeUpdated.setUserAddress(userAddress);
			tupleToBeUpdated.setUserName(userName);
			tupleToBeUpdated.setMobileNumber(mobileNumber);
			tupleToBeUpdated.setUserGrade(userGrade);
			tupleToBeUpdated.setJiFen(jiFen);
			tupleToBeUpdated.setUserPaid(userPaid);
		
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
