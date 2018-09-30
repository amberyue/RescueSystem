package domain;

import java.sql.Timestamp;

/**
 * Logins entity. @author MyEclipse Persistence Tools
 */

public class Logins implements java.io.Serializable {

	// Fields

	private Integer autoid;
	private Short authenType;
	private String userId;
	private String userName;
	private String ip;
	private String machineName;
	private Timestamp loginTime;

	// Constructors

	/** default constructor */
	public Logins() {
	}

	/** full constructor */
	public Logins(Short authenType, String userId, String userName, String ip,
			String machineName, Timestamp loginTime) {
		this.authenType = authenType;
		this.userId = userId;
		this.userName = userName;
		this.ip = ip;
		this.machineName = machineName;
		this.loginTime = loginTime;
	}

	// Property accessors

	@Override
	public String toString() {
		return "Logins [autoid=" + autoid + ", authenType=" + authenType
				+ ", userId=" + userId + ", userName=" + userName + ", ip="
				+ ip + ", machineName=" + machineName + ", loginTime="
				+ loginTime + "]";
	}

	public Integer getAutoid() {
		return this.autoid;
	}

	public void setAutoid(Integer autoid) {
		this.autoid = autoid;
	}

	public Short getAuthenType() {
		return this.authenType;
	}

	public void setAuthenType(Short authenType) {
		this.authenType = authenType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMachineName() {
		return this.machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

}