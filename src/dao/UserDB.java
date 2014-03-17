package dao;

import java.util.HashMap;

public class UserDB {

	private static HashMap <String, User> userHash= new HashMap <String, User> (); 
	
	public UserDB() {
		// TODO Auto-generated constructor stub
	}
	
	public static User getUser (String userName){
		return userHash.get(userName);
	}
	
	public static Boolean addUser (String userName, User userAdded ){
		 return  (userHash.put(userName, userAdded) == null) ? false : true;  
	}
}
