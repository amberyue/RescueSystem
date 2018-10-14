package bean;


import java.util.Date;
import java.util.Map;

import domain.Users;

public class loginResult {

	private String retcode;
	private String msg;
	private Map data;
	private Users user;
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
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
