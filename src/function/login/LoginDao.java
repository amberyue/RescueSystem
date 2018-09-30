package function.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import utils.DBUtils;
import bean.BasicResult;
import bean.loginResult;
import domain.Users;

public class LoginDao {
	
	public loginResult login(String userId,String pwd,String id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Users u = null;
	    loginResult loginUser=new loginResult();
	    String userName=null;
		try {
			conn = DBUtils.getConnection();
			String sql = "select u.userName,u.sex,u.birthday,u.address,u.email,u.nation from users u where u.UserID=? and u.pwd=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
		//	System.out.println(rs.next());
			
			loginUser.setRetcode("2");
			loginUser.setMsg("登陆用户名不存在");
			while(rs.next()){
				userName=rs.getString(1);
				loginUser.setRetcode("0"); 
				loginUser.setSessionid(id);
				loginUser.setMsg("");
				loginUser.setUserName(userName);
				loginUser.setSex(rs.getString(2));
				loginUser.setBirthday(rs.getDate(3));
				loginUser.setAddress(rs.getString(4));
				loginUser.setEmail(rs.getString(5));
				loginUser.setNation(rs.getString(6));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		return loginUser;
	}
	
	public void loginRecord(String userId,String userName,String ip,Timestamp time) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
			conn = DBUtils.getConnection();
			String sql = "insert into logins(authenType,userId,userName,ip,machineName,loginTime) values (?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setShort(1, (short)1);
			ps.setString(2, userId);
			ps.setString(3,userName);
			ps.setString(4,ip);
			ps.setString(5,"");
			ps.setTimestamp(6, time);
			int i=ps.executeUpdate();
			
			System.out.println(i);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		
	}
}
