package dao;

public class User {
    private String wxId; 
    private String address;
    private int transState; 
    private int registerState; 
    private long lastTransTime; 
    
	public long getLastTransTime() {
		return lastTransTime;
	}

	public void setLastTransTime(long lastTransTime) {
		this.lastTransTime = lastTransTime;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTransState() {
		return transState;
	}

	public void setTransState(int transState) {
		this.transState = transState;
	}

	public int getRegisterState() {
		return registerState;
	}

	public void setRegisterState(int registerState) {
		this.registerState = registerState;
	}

	public User(String wxID) {
		// TODO Auto-generated constructor stub
		this.wxId = wxID;
		this.address = null;
		this.transState = 0;
		this.registerState = 0; 
	}

}
