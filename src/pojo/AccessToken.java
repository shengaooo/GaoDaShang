package pojo;

import java.util.Date;

public class AccessToken {
    private static String token;  
    private static int expiresIn; 
    private static Date obsDate; 
  
    public static String getToken() {
    	//TODO we need check if the Token is expired.
        return token;  
    }  
  
    public static void  setToken(String ptoken) {  
        token = ptoken;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
    
    @SuppressWarnings("unchecked")
    public void setExpiresDate(int expInSeconds) {
    	Date curDate= new Date();

		int seconds=  curDate.getSeconds();
		obsDate=(Date) curDate.clone();
		
		//To make it safe, 5mins shorter than the expire time from weixin. 
		seconds += (expInSeconds>300 ? expInSeconds-300: expInSeconds);     
		obsDate.setSeconds(seconds);

    }
    public boolean isAccessTokenExpire(){
    	Date curDate= new Date(); 
    	int gap=obsDate.compareTo(curDate); 
    	return (gap<0)?true:false;  
    	
    }
}
