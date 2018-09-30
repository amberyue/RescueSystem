package domain;

import java.sql.Timestamp;

/**
 * Emergencyevents entity. @author MyEclipse Persistence Tools
 */

public class Emergencyevents implements java.io.Serializable {

	// Fields

	private Integer autoid;
	private Short status;
	private Timestamp createTime;
	private String callerId;
	private String callerName;
	private Double latitude;
	private Double longitude;
	private Short result;
	private String remark;

	// Constructors

	/** default constructor */
	public Emergencyevents() {
	}

	/** full constructor */
	public Emergencyevents(Short status, Timestamp createTime, String callerId,
			String callerName, Double latitude, Double longitude, Short result,
			String remark) {
		this.status = status;
		this.createTime = createTime;
		this.callerId = callerId;
		this.callerName = callerName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.result = result;
		this.remark = remark;
	}

	// Property accessors

	public Integer getAutoid() {
		return this.autoid;
	}

	public void setAutoid(Integer autoid) {
		this.autoid = autoid;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCallerId() {
		return this.callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getCallerName() {
		return this.callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Short getResult() {
		return this.result;
	}

	public void setResult(Short result) {
		this.result = result;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}