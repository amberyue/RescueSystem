package domain;

import java.sql.Timestamp;

/**
 * Relativepictures entity. @author MyEclipse Persistence Tools
 */

public class Relativepictures implements java.io.Serializable {

	// Fields

	private Integer autoid;
	private Short kind;
	private String id;
	private String title;
	private Integer imageId;
	private String creatorId;
	private String creatorName;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public Relativepictures() {
	}

	/** full constructor */
	public Relativepictures(Short kind, String id, String title,
			Integer imageId, String creatorId, String creatorName,
			Timestamp createTime) {
		this.kind = kind;
		this.id = id;
		this.title = title;
		this.imageId = imageId;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
		this.createTime = createTime;
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getImageId() {
		return this.imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
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

}