package database;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session; 
import org.hibernate.Transaction;

import database.MenuList;


public class MenuListManager extends TableManager {
	public Integer AddMenuList(Integer restaurantId, Integer mealSetHitCount, Byte isNew,
			                   String menuImagesPath, String mealSetName,
			                   String mealSetDescription, Double mealSetPrice,
			                   Double mealSetDiscount) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		Integer menuID = null;
		
		try {
			tx = session.beginTransaction();
			MenuList oneMenu = new MenuList(restaurantId, mealSetHitCount, isNew, menuImagesPath, mealSetName,
					                        mealSetDescription, mealSetPrice, mealSetDiscount);
			menuID = (Integer) session.save(oneMenu);
			tx.commit();
			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}		
		return menuID;		
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<MenuList> ListAllMenuList() {
		Iterator<MenuList> menuIterator = null;
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			menuIterator = (Iterator<MenuList>)(session.createQuery("FROM MenuList").list().iterator());
			tx.commit();			
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
	        e.printStackTrace(); 
		} finally {
			session.close();
		}
		return menuIterator;
	}
	
	public void DeleteMenuList(Integer menuID) {
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			MenuList menuToBeDeleted = (MenuList)session.get(MenuList.class, menuID);
			session.delete(menuToBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Integer UpdateMenuList(Integer menuID, Integer restaurantId, Integer mealSetHitCount, Byte isNew,
            String menuImagesPath, String mealSetName,
            String mealSetDescription, Double mealSetPrice,
            Double mealSetDiscount) {		
		Session session = mySessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			MenuList menuToBeUpdated = (MenuList)session.get(MenuList.class, menuID);
			
			menuToBeUpdated.setIsNew(isNew);
			menuToBeUpdated.setMealSetDescription(mealSetDescription);
			menuToBeUpdated.setMealSetDiscount(mealSetDiscount);
			menuToBeUpdated.setMealSetName(mealSetName);
			menuToBeUpdated.setMealSetPrice(mealSetPrice);
			menuToBeUpdated.setMenuImagesPath(menuImagesPath);
			menuToBeUpdated.setRestaurantId(restaurantId);
			
			session.update(menuToBeUpdated);
			tx.commit();
		
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close();
		}		
		return menuID;		
		}	
	
	}
