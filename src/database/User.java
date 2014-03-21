package database;

// Generated Mar 21, 2014 4:30:53 PM by Hibernate Tools 4.0.0

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private Integer userId;
	private String userAddress;
	private String userName;
	private String mobileNumber;
	private Integer userGrade;
	private Integer jiFen;
	private Double userPaid;

	public User() {
	}

	public User(String userAddress, String userName, String mobileNumber) {
		this.userAddress = userAddress;
		this.userName = userName;
		this.mobileNumber = mobileNumber;
	}

	public User(String userAddress, String userName, String mobileNumber,
			Integer userGrade, Integer jiFen, Double userPaid) {
		this.userAddress = userAddress;
		this.userName = userName;
		this.mobileNumber = mobileNumber;
		this.userGrade = userGrade;
		this.jiFen = jiFen;
		this.userPaid = userPaid;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getUserGrade() {
		return this.userGrade;
	}

	public void setUserGrade(Integer userGrade) {
		this.userGrade = userGrade;
	}

	public Integer getJiFen() {
		return this.jiFen;
	}

	public void setJiFen(Integer jiFen) {
		this.jiFen = jiFen;
	}

	public Double getUserPaid() {
		return this.userPaid;
	}

	public void setUserPaid(Double userPaid) {
		this.userPaid = userPaid;
	}

}
