package database;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session; 
import org.hibernate.Transaction;

import database.UserHabbit;


public class UserHabbitManager extends TableManager {
	public Integer AddUserHabbit(Integer userId, Integer menuId, Integer hitCounter ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		Integer primaryKey = null;
		
		try {
			tx = session.beginTransaction();
			UserHabbit myTuple = new UserHabbit(userId, menuId, hitCounter );
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
	public Iterator<UserHabbit> ListAllUserHabbit() {
		Iterator<UserHabbit> myIterator = null;
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			myIterator = (Iterator<UserHabbit>)(session.createQuery("FROM UserHabbit").list().iterator());
			tx.commit();			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}
		return myIterator;
	}
	
	public void DeleteUserHabbit(Integer primaryKey) {
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			UserHabbit tupleToBeDeleted = (UserHabbit)session.get(UserHabbit.class, primaryKey);
			session.delete(tupleToBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdateUserHabbit(Integer primaryKey, Integer userId, Integer menuId, Integer hitCounter ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			UserHabbit tupleToBeUpdated = (UserHabbit)session.get(UserHabbit.class, primaryKey);
			
			tupleToBeUpdated.setUserId(userId);
			tupleToBeUpdated.setMenuId(menuId);
			tupleToBeUpdated.setHitCounter(hitCounter);
		
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
