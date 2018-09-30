package bean;

import java.util.Map;

public class BasicResult {

	
	private String retcode;
	private String msg;
	private Map data;
	
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	private String sessionid;
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	
}
