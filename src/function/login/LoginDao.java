package function.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.C3P0Util;
import utils.DBUtils;
import bean.loginResult;
import domain.Contactlist;
import domain.Users;

public class LoginDao {
	
	public loginResult login(String userId,String pwd) {

		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select u.userName,u.sex,u.birthday,u.address,u.email,u.nation,u.tel,u.pwd,u.address from users u where u.UserID=? and u.pwd=?";
		
		try {
			List<Users> users=qr.query(sql,new BeanListHandler<Users>(Users.class),userId,pwd);
			loginResult loginResult = new loginResult();
			if(users.size()!=0){
				loginResult.setUser(users.get(0));
				loginResult.setRetcode("0");
				loginResult.setMsg("");
			}
			else{
				loginResult.setMsg("登陆用户名不存在");
				loginResult.setRetcode("2");
				
			}	
			    return loginResult;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public Contactlist contactList(String UserID){
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from contactlist where UserID=?";
		try {
			List<Contactlist> contact = qr.query(sql, new BeanListHandler<Contactlist>(Contactlist.class),UserID);
			if (contact.size()!=0){
				 return contact.get(0);
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void loginRecord(String userId,String userName,String ip,Timestamp time) {

	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	String sql = "insert into logins(authenType,userId,userName,ip,machineName,loginTime) values (?,?,?,?,?,?)";
	   try {
		qr.update(sql,(short)1, userId,userName,ip,"",time);
		System.out.println("111");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
