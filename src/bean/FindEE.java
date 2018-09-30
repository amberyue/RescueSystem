package bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FindEE {
 
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String CallerName;
	private Timestamp CreateTime;
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getCallerName() {
		return CallerName;
	}
	public void setCallerName(String callerName) {
		CallerName = callerName;
	}
	public Timestamp getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Timestamp createTime) {
		CreateTime = createTime;
	}
	
}
