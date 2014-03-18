package logintest;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.apache.log4j.Logger;

import service.CoreService;
import util.SignUtil;

public class CoreServlet extends HttpServlet {
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
        
        String respMessage = CoreService.processRequest(request); 
        
        logger.error("respMessage is: "+respMessage); 
        
        // 响应消息   
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close();  
    }  

	
}
