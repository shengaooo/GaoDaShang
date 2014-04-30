package pojo;

public class PushTxtMsgToUser {
	public PushTxtMsgToUser(String touser, TextMsg text) {
		this.touser = touser;
		this.text = text;
		this.msgtype = "text"; 
	}
    TextMsg text; 
    String msgtype;
	String touser; 
    
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public TextMsg getText() {
		return text;
	}
	public void setText(TextMsg text) {
		this.text = text;
	} 
	
	
}
