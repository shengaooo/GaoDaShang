package util;
import java.io.InputStream;  
import java.io.Writer;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  


import javax.servlet.http.HttpServletRequest;  

import message.res.*;

import org.dom4j.Document;  
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;  

import com.thoughtworks.xstream.XStream;  
import com.thoughtworks.xstream.core.util.QuickWriter;  
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;  
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;  
import com.thoughtworks.xstream.io.xml.XppDriver;  

public class MessageUtil { 
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";   
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
  
	
    public MessageUtil() {
    	System.out.println("Constructor of MessageUtil");
    }
    
    /** 
     * Decode WX's Request(XML) 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
	    @SuppressWarnings("unchecked")  
	    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
	        // Store the result into HashMap   
	        Map<String, String> map = new HashMap<String, String>();  
	  
	        // 从request中取得输入流     
	        InputStream inputStream = request.getInputStream();  
	        // 读取输入流      
	        SAXReader reader = new SAXReader();  
	        Document document = reader.read(inputStream);  
	        // 得到xml根元素      
	        Element root = document.getRootElement();  
	        // 得到根元素的所有子节点   
	        List<Element> elementList = root.elements();  
	  
	        // 遍历所有子节点    
	        for (Element e : elementList)  
	            map.put(e.getName(), e.getText());  
	  
	        // 释放资源     
	        inputStream.close();  
	        inputStream = null;  
	  
	        return map;  
	    }  
	  
	    /** 
	     * Change TextResponse to xml 
	     *  
	     * @param textMessage ?????? 
	     * @return xml 
	     */  
	    public static String textMessageToXml(TextResponse textMessage) {  
	        xstream.alias("xml", textMessage.getClass());  
	        return xstream.toXML(textMessage);  
	    }  
	  
	    /** 
	     * Change Music response to xml 
	     *  
	     * @param musicMessage ?????? 
	     * @return xml 
	     */  
	    public static String musicMessageToXml(MusicResponse musicMessage) {  
	        xstream.alias("xml", musicMessage.getClass());  
	        return xstream.toXML(musicMessage);  
	    }  
	  
	    /** 
	     * Change News Response to xml 
	     *  
	     * @param newsMessage ?????? 
	     * @return xml 
	     */  
	    public static String newsMessageToXml(NewsResponse newsMessage) {  
	        xstream.alias("xml", newsMessage.getClass());  
	        xstream.alias("item", new Article().getClass());  
	        return xstream.toXML(newsMessage);  
	    }  
	  
	    /** 
	     * Extend xstream to enable it to support CDATA? 
	     *  
	     * @date 2013-05-19 
	     */  
	    private static XStream xstream = new XStream(new XppDriver() {  
	        public HierarchicalStreamWriter createWriter(Writer out) {  
	            return new PrettyPrintWriter(out) {  
	            	// 对所有xml节点的转换都增加CDATA标记     
	                boolean cdata = true;  
	  
	                @SuppressWarnings("unchecked")  
	                public void startNode(String name, Class clazz) {  
	                    super.startNode(name, clazz);  
	                }  
	  
	                protected void writeText(QuickWriter writer, String text) {  
	                    if (cdata) {  
	                        writer.write("<![CDATA[");  
	                        writer.write(text);  
	                        writer.write("]]>");  
	                    } else {  
	                        writer.write(text);  
	                    }  
	                }  
	            };  
	        }  
	    });  
}  


