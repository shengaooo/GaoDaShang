package message.res;

public class BaseResponse {
	// 接收方帐号（收到的OpenID）   
    private String ToUserName;  
    // 开发者微信号   
    private String FromUserName;  
   // 消息创建时间 （整型）     
    private long CreateTime;  
    //消息类型（text/music/news）      
    private String MsgType;  
    //  位0x0001被标志时，星标刚收到的消息   ??  微信规范中没定义这个字段。 不知道干啥用。  
    private int FuncFlag;  
  
    public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(long createTime) {  
        CreateTime = createTime;  
    }  
}