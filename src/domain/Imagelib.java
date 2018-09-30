package domain;

/**
 * Imagelib entity. @author MyEclipse Persistence Tools
 */

public class Imagelib implements java.io.Serializable {

	// Fields

	private Integer autoid;
	private String ext;
	private String img;

	// Constructors

	/** default constructor */
	public Imagelib() {
	}

	/** full constructor */
	public Imagelib(String ext, String img) {
		this.ext = ext;
		this.img = img;
	}

	// Property accessors

	public Integer getAutoid() {
		return this.autoid;
	}

	public void setAutoid(Integer autoid) {
		this.autoid = autoid;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}