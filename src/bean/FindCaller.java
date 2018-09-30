package bean;

import java.math.BigDecimal;
import java.util.Date;

public class FindCaller {

	private String CallerName;
	private BigDecimal Latitude;
	private BigDecimal Longitude;
	@Override
	public String toString() {
		return "FindCaller [CallerName=" + CallerName + ", Latitude="
				+ Latitude + ", Longitude=" + Longitude + ", sex=" + sex
				+ ", birthday=" + birthday + ", tel=" + tel + "]";
	}
	private String sex;
	private Date birthday;
	private String tel;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCallerName() {
		return CallerName;
	}
	public void setCallerName(String callerName) {
		CallerName = callerName;
	}
	public BigDecimal getLatitude() {
		return Latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		Latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return Longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		Longitude = longitude;
	}
	
	
}
