package domain;

/**
 * Contactlist entity. @author MyEclipse Persistence Tools
 */

public class Contactlist implements java.io.Serializable {

	// Fields

	private Integer autoid;
	private String userId;
	private String cname;
	private String telNo;
	private String relationShip;

	// Constructors

	/** default constructor */
	public Contactlist() {
	}

	/** full constructor */
	public Contactlist(String userId, String cname, String telNo,
			String relationShip) {
		this.userId = userId;
		this.cname = cname;
		this.telNo = telNo;
		this.relationShip = relationShip;
	}

	// Property accessors

	public Integer getAutoid() {
		return this.autoid;
	}

	public void setAutoid(Integer autoid) {
		this.autoid = autoid;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getRelationShip() {
		return this.relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

}