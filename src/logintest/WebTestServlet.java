package logintest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.log4j.Logger;

import database.User;
import database.PlaceOrder;
import service.CoreService;
import util.SignUtil;

public class WebTestServlet extends HttpServlet {
	private static final long serialVersionUID = -925347065302140136L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(CoreServlet.class);
        logger.error("doGet is called v18.16.37"); 
		
		String signature = request.getParameter("signature");  
		String timestamp = request.getParameter("timestamp");  
		String nonce = request.getParameter("nonce");  
		String echostr = request.getParameter("echostr");  
		
		PrintWriter out = response.getWriter();  
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);  
		}
		else {
			logger.error("Authentication fails");
		}
		out.close();  
		out = null;  
	}
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      	

        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        
        Logger logger = Logger.getLogger(CoreServlet.class);
        logger.error("doPost is called"); 
        
//        String respMessage = CoreService.processRequest(request); 
        
        //For DB test
        String userName = request.getParameter("FromUserName");
        String method = request.getParameter("Method");
        String respMessage = "default";
        
        if (method.equalsIgnoreCase("addr")){        	
        	respMessage= CoreService.func_M2_MyAddr(userName);
        } else if (method.equalsIgnoreCase("tele")){
        	respMessage= CoreService.func_M2_MyMob(userName);
        } else if (method.equalsIgnoreCase("upd")){
        	String content = request.getParameter("UpdContent");
        	int cmd=CoreService.analCmd(content);
        		respMessage=CoreService.func_M2_updAddr(userName, content,cmd);
        } else if (method.equalsIgnoreCase("addOder")){
        	String content = request.getParameter("OrderContent"); 
        	int cmd=CoreService.analCmd(content);
        	System.out.println("CMD after analysis is: "+cmd); 
        	respMessage=CoreService.func_M1_orderFood(userName, content, cmd);
        } else if (method.equalsIgnoreCase("queryOrder")){
        	respMessage=CoreService.func_M1_MyOrder(userName);
        }
        
        logger.error("respMessage is: "+respMessage); 
        
        // 响应消息   
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close();  
    }  	
}
