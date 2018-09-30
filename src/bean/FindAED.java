package bean;

import java.math.BigDecimal;

public class FindAED {

	private BigDecimal latitude;
	private BigDecimal longitude;
	private String address;
	
	@Override
	public String toString() {
		return "FindAED [latitude=" + latitude + ", longitude=" + longitude
				+ ", address=" + address + "]";
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
