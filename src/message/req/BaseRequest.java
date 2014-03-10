package message.req;

public class BaseRequest {
    // å¼€å�‘è€…å¾®ä¿¡å�·  
    private String ToUserName;  
    // å�‘é€�æ–¹å¸�å�·ï¼ˆä¸€ä¸ªOpenIDï¼‰      
    private String FromUserName;  
    // // æ¶ˆæ�¯åˆ›å»ºæ—¶é—´ ï¼ˆæ•´åž‹ï¼‰     
    private long CreateTime;  
    // æ¶ˆæ�¯ç±»åž‹ï¼ˆtext/image/location/linkï¼‰     
    private String MsgType;  
    // æ¶ˆæ�¯idï¼Œ64ä½�æ•´åž‹      
    private long MsgId;  
  
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
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  
  
    public long getMsgId() {  
        return MsgId;  
    }  
  
    public void setMsgId(long msgId) {  
        MsgId = msgId;  
    }  

}
