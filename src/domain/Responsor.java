package domain;

import java.math.BigDecimal;

public class Responsor {
	 
	private String UserID;
	private String UserName;
//	private BigDecimal Latitude;
//	private BigDecimal Longitude;
	private String  Tel;
	
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
//	public BigDecimal getLatitude() {
//		return Latitude;
//	}
//	public void setLatitude(BigDecimal latitude) {
//		Latitude = latitude;
//	}
//	public BigDecimal getLongitude() {
//		return Longitude;
//	}
//	public void setLongitude(BigDecimal longitude) {
//		Longitude = longitude;
//	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	
}
