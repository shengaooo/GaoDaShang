package service;
import java.util.Date;                         
import java.util.Map;                          

import javax.servlet.http.HttpServletRequest;  

import dao.UserDB;
import dao.User; 
import message.res.TextResponse;
import ui.menu.*;
import util.MessageUtil;

public class CoreService {
	public CoreService(){
		System.out.println("In constructor of CoreService");
	}
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容     
            String respContent = "请求处理异常，请稍候尝试！";  
            
            // xml请求解析   
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）     
            String fromUserName = requestMap.get("FromUserName");  
            
            //Log
            System.out.println("From User: "+fromUserName);
            
            //  公众帐号     
            String toUserName = requestMap.get("ToUserName");  
            //Log
            System.out.println("To User: "+toUserName);
            
            // 消息类型    
            String msgType = requestMap.get("MsgType");  
            //Log
            System.out.println("MsgType: "+msgType);      
            
            // 消息内容    
            String msgContent = requestMap.get("Content").trim();  
            //Log
            System.out.println("msgContent: "+msgContent);               
  
            String createTime=  requestMap.get("CreateTime"); 
            //Log
            System.out.println("createTime: "+createTime); 
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
            	User tempUser = UserDB.getUser(fromUserName);
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
                    // TODO 自定义菜单权没有开放，暂不处理该类消息     
                }  
            }  
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
}
