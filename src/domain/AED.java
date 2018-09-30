package domain;

import java.math.BigDecimal;

public class AED {
 
	private int autoid;
	private short status;
	private String address;
	private Double longitude;
	private Double latitude;
	public synchronized int getAutoid() {
		return autoid;
	}
	public synchronized void setAutoid(int autoid) {
		this.autoid = autoid;
	}
	public synchronized short getStatus() {
		return status;
	}
	public synchronized void setStatus(short status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
}
