package service;
import java.util.Date; 

import database.*;                        

import java.util.Map;                          

import javax.servlet.http.HttpServletRequest;  

import org.apache.log4j.Logger;

import pojo.AccessToken;
import pojo.PushMsgText;
import pojo.TextContent;
import message.res.TextResponse;
import ui.menu.*;
import util.MessageUtil;
import util.WeixinUtil;

public class CoreService {
	final public static int UPD_ADDR =1; 
	final public static int UPD_MOB =2; 
	final public static int FOOD_ORDER_CMD = 3; 
	
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
	
	private static String func_M1_AllMenu () {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforAllMenu;
	}

	private static String func_M1_Hot (String tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforHotMenu;
	}
	
	private static String func_M1_MyFav (String tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforFavMenu;
	}	
	
	private static String func_M1_New (String tempUser) {
		//设置用户当前交易状态
		//读取所有菜单
		//返回所有菜单
		return testResponseforNewMenu;
	}	

	public static String func_M1_MyOrder (String userName) {
		String reply; 
		User tempUser = User.getUserFromDBbyName(userName);
		int userID=tempUser.getUserId();
		reply = PlaceOrder.queryOrder(userID); 
		return reply;
	}
	public static String func_M2_MyAddr (String UserName) {
		String reply; 
		User fUser= User.getUserFromDBbyName(UserName);

		String tempAddr=fUser.getUserAddress(); 
		if (tempAddr.equals(" ")){
			reply="您的地址为空，请您回复 DZ您的地址 来更新您的地址\n";  
		} else {
			reply="您的地址是： "+ tempAddr+"\n 如果您想更新您的地址请回复 DZ您的地址 来更新您的地址\n";
		}
		return reply;
	}		
	
	public static String func_M2_MyMob (String UserName) {
		User fUser= User.getUserFromDBbyName(UserName);
		String reply; 
		String tempMob=fUser.getMobileNumber(); 
		if (tempMob.equals(" ")){
			reply="您的电话为空，请您回复 DH您的电话 来更新您的电话\n";
		} else {
			reply="您的电话为： "+ tempMob+"\n 如果您想更新您的电话请回复  DH您的电话 来更新您的电话\n";
//			reply="Your mobile is: "+ tempMob+ "\n; pls reply with DH+Mobile to update";
		}
		return reply;
	}	
	
	private static String MenuClickProcess (String eventKey, String tempUser){
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
    			respContent=func_M1_AllMenu();
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
    	  case MenuClick.M1_MyOrder: {
    		 respContent=func_M1_MyOrder(tempUser);
    		 break; 
    	  }
    	  case MenuClick.M2_MyAddr:{
    		respContent= func_M2_MyAddr(tempUser);
    		break;
    	  }
    	  case MenuClick.M2_MyPhone:{
    		  respContent=func_M2_MyMob(tempUser);
    	  }
    	  
    	}; 
    	return respContent; 
	}
	
	public static int analCmd(String cmd){
		String cmdUpperCase=cmd.toUpperCase();
		
		if (cmdUpperCase.startsWith("DZ")) { 
			return UPD_ADDR; 
		} else if (cmdUpperCase.startsWith("DH")) {
			return UPD_MOB; 
		} else {
			return FOOD_ORDER_CMD; 
		}
//		return 0; 
	}
    public static String processRequest(HttpServletRequest request) { 
    	Logger logger = Logger.getLogger(CoreService.class); 
    	
        String respMessage = null, eventKey;
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
              int cmd= analCmd(msgContent);
              logger.error("Command after analyse is: "+cmd);
              
              switch (cmd) {
              case UPD_ADDR:{ respContent=func_M2_updAddr(fromUserName, msgContent,cmd); break; }
              case UPD_MOB:{ respContent=func_M2_updMob(fromUserName, msgContent,cmd); break; }
              case FOOD_ORDER_CMD: { respContent = func_M1_orderFood(fromUserName,msgContent,cmd); break; }
              }
              //Log
              logger.error("msgContent: "+msgContent);          
              
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
                	respContent = MenuClickProcess(eventKey, fromUserName); 

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

	public static String func_M2_updMob(String fromUser, String msgContent, int cmd) {
		User.updUserInfoToDB(fromUser,msgContent,cmd);
		return "您已经成功更新手机信息"; 
	}

	public static String func_M2_updAddr(String fromUser, String msgContent, int cmd) {
		// TODO Auto-generated method stub
		User.updUserInfoToDB(fromUser,msgContent,cmd); 
		return "您已经成功更新您的地址"; 
	}  
	
	public static String func_M1_orderFood(String fromUser, String msgContent, int cmd) {
		PlaceOrder.processOrder(fromUser, msgContent, cmd);
		return "您已经成功订餐"; 
	}
	
	public static int func_pushTxtMsg( String fromUser, String msgContent){
		String tempToken=AccessToken.getToken(); 
		TextContent tc=new TextContent (msgContent); 
		PushMsgText pmt=new PushMsgText(fromUser, tc); 
		int result = WeixinUtil.sendMsg(pmt, tempToken);  
		return result; 
	}
}
