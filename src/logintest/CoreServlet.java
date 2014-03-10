package logintest;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.SignUtil;

public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = -925347065302140136L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		System.out.println("This is for test for webchat");
		
		String signature = request.getParameter("signature");  
		String timestamp = request.getParameter("timestamp");  
		String nonce = request.getParameter("nonce");  
		String echostr = request.getParameter("echostr");  
		
		PrintWriter out = response.getWriter();  
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);  
		}
		else {
			System.out.println("Authentication fails");
		}
		out.close();  
		out = null;  
	}
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO ???????????  
    	
        // TODO   
    }  

	
}
