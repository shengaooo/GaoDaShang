package util;
import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.ConnectException;  
import java.net.URL;  
import javax.net.ssl.HttpsURLConnection;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;  
import logintest.CoreServlet;
import org.apache.log4j.Logger;
import pojo.AccessToken;
import pojo.Menu;
import pojo.PushTxtMsgToUser;
import service.CoreService;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;  

public class WeixinUtil {
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="
			+ "client_credential&appid=APPID&secret=APPSECRET";
	
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
			+ "ACCESS_TOKEN";
	
	public static String push_msg_url ="https://api.weixin.qq.com/cgi-bin/message/custom/"
			+ "send?access_token=ACCESS_TOKEN"; 
	
	/** 
	 	     * 发起https请求并获取结果 
	 	     *  
	 	     * @param requestUrl 请求地址 
	 	     * @param requestMethod 请求方式（GET、POST） 
	 	     * @param outputStr 提交的数据 
	 	     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
	 	     */  

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化   
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            
            // 从上述SSLContext对象中得到SSLSocketFactory对象   
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  

            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod)) {
            	httpUrlConn.connect();
            } else if ("POST".equalsIgnoreCase(requestMethod)){
            	httpUrlConn.connect();
            }
                  
            
            
  
            //当有数据需要提交时   
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码     
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串   
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源    
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
//            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
//            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }    
    
    //获取access_token 
	public static AccessToken getAccessToken(String appid, String appsecret) {  
	    AccessToken accessToken = null;  
	    
        Logger logger = Logger.getLogger(WeixinUtil.class);	 
        logger.error("Get Access Token is called");
	  
	    String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  

	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessToken();  
	            accessToken.setToken(jsonObject.getString("access_token")); 
	            int expires_in=jsonObject.getInt("expires_in");
	            accessToken.setExpiresIn(expires_in); 
	            accessToken.setExpiresDate(expires_in);
	        } catch (JSONException e) {  
	            accessToken = null;  
	            // ??token??   
//	            log.error("??token?? errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return accessToken;  
	}  

	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url      
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串     
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单     
	    JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
//	            log.error("?????? errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    } 
	    return result;  
	}  
	
	public static int sendMsg(PushTxtMsgToUser msg, String accessToken) {
		Logger logger = Logger.getLogger(WeixinUtil.class); 
	    String url = push_msg_url.replace("ACCESS_TOKEN", accessToken);  
	    int result=0; 
	    String jsonMsg = JSONObject.fromObject(msg).toString();  
	    logger.error("The JSON Message is: "+jsonMsg);
	    
	    JSONObject jsonObject = httpRequest(url,"POST",jsonMsg); 
	    
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
	            logger.error("Error code is: "+result + "Error Message is: "+jsonObject.getString("errmsg")); 
	        }  
	    } 
	    return result;  
	}
}
