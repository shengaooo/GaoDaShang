package database;

// Generated Mar 21, 2014 4:30:53 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * PlaceOrder generated by hbm2java
 */
public class PlaceOrder implements java.io.Serializable {

	private Integer orderId;
	private Integer tableId;
	private Integer orderCount;
	private Date orderTime;
	private Date obsoleTime;
	private Integer userId;
	private String orderList;
	private Double prepaidAmount;
	private Double totalAmout;

	public PlaceOrder() {
	}

	public PlaceOrder(Integer tableId, Integer orderCount, Date orderTime,
			Date obsoleTime, Integer userId, String orderList,
			Double prepaidAmount, Double totalAmout) {
		this.tableId = tableId;
		this.orderCount = orderCount;
		this.orderTime = orderTime;
		this.obsoleTime = obsoleTime;
		this.userId = userId;
		this.orderList = orderList;
		this.prepaidAmount = prepaidAmount;
		this.totalAmout = totalAmout;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getTableId() {
		return this.tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getObsoleTime() {
		return this.obsoleTime;
	}

	public void setObsoleTime(Date obsoleTime) {
		this.obsoleTime = obsoleTime;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderList() {
		return this.orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public Double getPrepaidAmount() {
		return this.prepaidAmount;
	}

	public void setPrepaidAmount(Double prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}

	public Double getTotalAmout() {
		return this.totalAmout;
	}

	public void setTotalAmout(Double totalAmout) {
		this.totalAmout = totalAmout;
	}
	
	@SuppressWarnings("unchecked")
	public static void processOrder(String fromUser, String msgContent, int cmd) {
		//TODO - Check the menu from the DB with the menuID.
		
		//TODO - Increase the MenuList counter; 
		
		//TODO - Get the user habit with the User name and MenuID. Add a new one or increase the counter if there is existing one.
		
		User tempUser=User.getUserFromDBbyName(fromUser);
		int userID=tempUser.getUserId();
		
		Configuration configuration = new Configuration().configure();   
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();  
	    SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);  
	    Session ss=sf.openSession();
		
		//Assume the order is like this A370 1 A380 2
		String [] orderList=msgContent.trim().replaceAll(" +", " ").split(" "); 

		for (int i=0; i<orderList.length/2; i++) {
			PlaceOrder tempPlaceOrder = new PlaceOrder();
			Date curDate = new Date();
			tempPlaceOrder.setOrderTime(curDate);
			

			int hrs=  curDate.getHours();
			Date obsDate=(Date) curDate.clone();
			hrs += 8;   //Assume Order expires 8 hours later
			obsDate.setHours(hrs);
			tempPlaceOrder.setObsoleTime(obsDate);
			
			String order=orderList[i*2]; 
			int counter=Integer.parseInt(orderList[i*2+1]); 
			tempPlaceOrder.setOrderList(order);
			tempPlaceOrder.setOrderCount(counter);			
			tempPlaceOrder.setUserId(userID);
			
		    Transaction ts= ss.beginTransaction();
		    ss.save(tempPlaceOrder);
		    ts.commit();			
		}
	}
	
	public static String queryOrder(int userID){
		String reply=null;
		
		Configuration configuration = new Configuration().configure();   
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();  
	    SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);  
	    Session ss=sf.openSession();
	    Transaction ts= ss.beginTransaction();
	    Query q=ss.createQuery("from PlaceOrder where UserID ='"+userID+"'");  
	    List qList=q.list();
	    ts.commit();
	    int qListSize=qList.size();
	    
	    if (qListSize==0){
	    	reply= "您当前没有订单";
	    } else {
    		reply ="您当前的订单如下：\n";
	    	for (int i=0; i<qListSize; i++){
		    	PlaceOrder tempOrder = (PlaceOrder) qList.get(i); 
		    	int no=i+1;
		    	reply += "订单第"+no+"号\n";
		    	reply += "套餐： "+tempOrder.getOrderList();
		    	reply += " 数量： "+tempOrder.getOrderCount()+"\n";
		    	reply += "订餐时间： "+tempOrder.getOrderTime().toString()+"\n";
		    	reply += "超时时间: "+tempOrder.getObsoleTime().toString()+"\n";
		    	reply +="\n"; 
	    	}
	    }
		return reply; 
	}

}
