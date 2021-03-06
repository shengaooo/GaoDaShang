package database;

// Generated Mar 21, 2014 4:30:53 PM by Hibernate Tools 4.0.0

/**
 * UserHabbit generated by hbm2java
 */
public class UserHabbit implements java.io.Serializable {

	private Integer habbitId;
	private Integer userId;
	private Integer menuId;
	private Integer hitCounter;

	public UserHabbit() {
	}

	public UserHabbit(Integer userId, Integer menuId, Integer hitCounter) {
		this.userId = userId;
		this.menuId = menuId;
		this.hitCounter = hitCounter;
	}

	public Integer getHabbitId() {
		return this.habbitId;
	}

	public void setHabbitId(Integer habbitId) {
		this.habbitId = habbitId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getHitCounter() {
		return this.hitCounter;
	}

	public void setHitCounter(Integer hitCounter) {
		this.hitCounter = hitCounter;
	}

}
