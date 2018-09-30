package domain;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Trainingrecords entity. @author MyEclipse Persistence Tools
 */

public class Trainingrecords implements java.io.Serializable {

	// Fields

	private Integer autoid;
	private Short kind;
	private String creatorId;
	private String creatorName;
	private Timestamp createTime;
	private String userId;
	private String userName;
	private String itemName;
	private Date date;
	private String teacher;
	private String place;
	private String score;
	private String remark;

	// Constructors

	/** default constructor */
	public Trainingrecords() {
	}

	/** full constructor */
	public Trainingrecords(Short kind, String creatorId, String creatorName,
			Timestamp createTime, String userId, String userName,
			String itemName, Date date, String teacher,
			String place, String score, String remark) {
		this.kind = kind;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
		this.createTime = createTime;
		this.userId = userId;
		this.userName = userName;
		this.itemName = itemName;
		this.date = date;
		this.teacher = teacher;
		this.place = place;
		this.score = score;
		this.remark = remark;
	}

	// Property accessors

	public Integer getAutoid() {
		return this.autoid;
	}

	public void setAutoid(Integer autoid) {
		this.autoid = autoid;
	}

	public Short getKind() {
		return this.kind;
	}

	public void setKind(Short kind) {
		this.kind = kind;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getTeacher() {
		return this.teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}