package service;
import java.util.Date;                         
import java.util.Map;                          
import javax.servlet.http.HttpServletRequest;  

import org.apache.log4j.Logger;

import dao.UserDB;
import dao.User; 
import message.res.TextResponse;
import ui.menu.*;
import util.MessageUtil;

public class CoreService {
	
	static String [] testResponseforMenu = new String [] {"请求处理异常，请稍候尝试！",
		"全部套餐列表被点击", 
		"我常吃的套餐 被点击",
		"热门套餐排行 被点击",
		"最新套餐 被点击",
		"查询我的订单  被点击",
		"我的地址 被点击",
		"我的电话 被点击",
		"我的积分 被点击",
		"我的优惠 被点击",
		"附近的高大上 被点击"};
	
	static final String testResponseforAllMenu = new String ("A1. 凉瓜牛肉 \n A2. 番茄炒蛋 \n "
			+ "A3.辣子鸡\n 请您回复餐号和份数， 中间以空格分开");
	
	static final String testResponseforFavMenu = new String ("B1. 凉瓜牛肉 \n B2. 番茄炒蛋 \n "
			+ "B3.辣子鸡\n 请您回复餐号和份数， 中间以空格分开");
	
	static final String testResponseforHotMenu = new String ("C1. 凉瓜牛肉 \n C2. 番茄炒蛋 \n "
			+ "C3.辣子鸡\n 请您回复餐号和份数， 中间以空格分开");
	
	static final String testResponseforNewMenu = new String ("D1. 凉瓜牛肉 \n D2. 番茄炒蛋 \n "
			+ "D3.辣子鸡\n 请您回复餐号和份数， 中间以空格分开");
	
	private static String func_M1_AllMenu (User tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforAllMenu;
	}

	private static String func_M1_Hot (User tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforHotMenu;
	}
	
	private static String func_M1_MyFav (User tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforFavMenu;
	}	
	
	private static String func_M1_New (User tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforNewMenu;
	}	

	
	private static String MenuClickProcess (String eventKey, User tempUser){
    	int intKey = 0; 
        // 默认返回的文本消息内容     
        String respContent = testResponseforMenu[0];  
    	try {
    		intKey = Integer.parseInt(eventKey); 
    	} catch (Exception e) {
    	   //Log 
    		return respContent; 
    	}
    	respContent= testResponseforMenu[intKey]; //ToBe Deleted when all function done. 
    	
    	switch  (intKey) 
    	{ case MenuClick.M1_AllMenu: 
    		{ 
    			respContent=func_M1_AllMenu(tempUser);
    			break; 
    		}
    	  case MenuClick.M1_Hot:{ 
			respContent=func_M1_Hot(tempUser);
			break; 
		  }
    	  case MenuClick.M1_MyFav:{ 
			respContent=func_M1_MyFav(tempUser);
			break; 
		  }    	 
    	  case MenuClick.M1_New:{ 
  			respContent=func_M1_New(tempUser);
  			break; 
  		  }
    	}; 
    	return respContent; 
	}
	
    public static String processRequest(HttpServletRequest request) { 
    	Logger logger = Logger.getLogger(CoreService.class); 
    	
        String respMessage = null, eventKey;
        User tempUser = null;
        logger.error("We are in processRequest");
        
        try {  
            // 默认返回的文本消息内容     
            String respContent = "请求处理异常，请稍候尝试！";  
            
            // xml请求解析   
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）     
            String fromUserName = requestMap.get("FromUserName"); 
            
            logger.error("FromUser is: "+fromUserName); 
            
            //  公众帐号     
            String toUserName = requestMap.get("ToUserName");  
            //Log
            logger.error("To User: "+toUserName);
            
            // 消息类型    
            String msgType = requestMap.get("MsgType");  
            //Log
            logger.error("MsgType: "+msgType);      
                      
  
            String createTime=  requestMap.get("CreateTime"); 
            //Log
            logger.error("createTime: "+createTime); 
            long createTimeInLong = Long.parseLong(createTime);
            
            
            // 回复文本消息    
            TextResponse textMessage = new TextResponse();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  

            // 文本消息      
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
//                 消息内容    
              String msgContent = requestMap.get("Content").trim();  
              //Log
              logger.error("msgContent: "+msgContent);    
            	
            	tempUser = UserDB.getUser(fromUserName);
            	if (tempUser == null) {
            		//This is a new User, display the menu of 0. 
                    if (msgContent.equals("2")) {
                    	User newUser = new User (fromUserName); 
                    	UserDB.addUser(fromUserName, newUser); 
                    	respContent= MenuClass.getMenu(1);
                    	newUser.setLastTransTime(createTimeInLong);
                    	
//   Don't know why, addUser return null; will try it later. ToDo.                   	
//                    	if (UserDB.addUser(fromUserName, newUser)){
//                    		respContent= MenuClass.getMenu(1);  //Menu 1: 要求输入地址
//                    		newUser.setLastTransTime(createTimeInLong);
//                    	} else {
//                    		respContent= MenuClass.getMenu(2);  //Menu2: 数据错误，让用户稍等
//                    	}
                    } else {
                    	respContent = MenuClass.getMenu(3)+ "\n\n"+ MenuClass.getMenu(0); //Menu3: 您还不是注册用户，请您先注册 
                    }
            	} else {
            		if (tempUser.getRegisterState()==0) {
            			//ToDo: We can check the time btw this action and last action. 
            			tempUser.setAddress(msgContent);
            			tempUser.setRegisterState(1);
            			tempUser.setLastTransTime(createTimeInLong);
            			respContent = MenuClass.getMenu(4)+msgContent; //Menu 4: 您已注册成功，您的注册地址是: 
            		} else {
            			if (msgContent.equals("1")) {
            				respContent = MenuClass.getMenu(5); 
            				tempUser.setTransState(1);
            				tempUser.setLastTransTime(createTimeInLong);
            			} 
            		}
            		
            	}
            }  
            // 图片消息      
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息!";  
            }  
            //地理位置消息      
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息!";  
            }  
            // 链接消息      
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息!";  
            }  
            // 音频消息   
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息!";  
            }  
            // 事件推送        
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型     
                String eventType = requestMap.get("Event");  
                
                logger.error("Event Process is called");
                logger.error("Event type is: "+eventType);
                
                // 订阅   
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = " 谢谢您的关注!";  
                }  
                // 取消订阅   
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                	// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息     
                }  
                // 自定义菜单点击事件     
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                	
                	eventKey = requestMap.get("EventKey");  
                	logger.error("EventKey is: "+eventKey);
                    	
                	respContent = MenuClickProcess(eventKey, tempUser); 

                }  
            }  
            //Log
            logger.error("Response Content is: "+ respContent); 
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return respMessage;  
    }  
}
