package database;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session; 
import org.hibernate.Transaction;

import database.PlaceOrder;


public class PlaceOrderManager extends TableManager {
	public Integer AddPlaceOrder(Integer tableId, Integer orderCount, Date orderTime, Date obsoleTime, Integer userId, String orderList, Double prepaidAmount, Double totalAmout ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		Integer primaryKey = null;
		
		try {
			tx = session.beginTransaction();
			PlaceOrder myTuple = new PlaceOrder(tableId, orderCount, orderTime, obsoleTime, userId, orderList, prepaidAmount, totalAmout );
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
	public Iterator<PlaceOrder> ListAllPlaceOrder() {
		Iterator<PlaceOrder> myIterator = null;
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			myIterator = (Iterator<PlaceOrder>)(session.createQuery("FROM PlaceOrder").list().iterator());
			tx.commit();			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}
		return myIterator;
	}
	
	public void DeletePlaceOrder(Integer primaryKey) {
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			PlaceOrder tupleToBeDeleted = (PlaceOrder)session.get(PlaceOrder.class, primaryKey);
			session.delete(tupleToBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdatePlaceOrder(Integer primaryKey, Integer tableId, Integer orderCount, Date orderTime, Date obsoleTime, Integer userId, String orderList, Double prepaidAmount, Double totalAmout ) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			PlaceOrder tupleToBeUpdated = (PlaceOrder)session.get(PlaceOrder.class, primaryKey);
			
			tupleToBeUpdated.setTableId(tableId);
			tupleToBeUpdated.setOrderCount(orderCount);
			tupleToBeUpdated.setOrderTime(orderTime);
			tupleToBeUpdated.setObsoleTime(obsoleTime);
			tupleToBeUpdated.setUserId(userId);
			tupleToBeUpdated.setOrderList(orderList);
			tupleToBeUpdated.setPrepaidAmount(prepaidAmount);
			tupleToBeUpdated.setTotalAmout(totalAmout);
		
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
