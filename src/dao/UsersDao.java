package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.C3P0Util;
import utils.DBUtils;
import bean.BasicResult;
import bean.FindVolunteer;
import bean.loginResult;
import domain.Users;

public class UsersDao {
	private List<FindVolunteer> list=new ArrayList();
	public List<FindVolunteer> search(BigDecimal minlat, BigDecimal maxlat, BigDecimal minlng, BigDecimal maxlng) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "select u.username,u.longitude,u.latitude from users u where u.roles=2 and u.longitude>='"+minlng+"' and u.longitude <='"+maxlng+"' and u.latitude>='"+minlat+"' and u.latitude<='"+maxlat+"'";
			ps = conn.prepareStatement(sql);
			System.out.println(sql);
			rs = ps.executeQuery();
			System.out.println(rs.next());
			
			int i=0;
			while (rs.next()){
				
				FindVolunteer volunteer=new FindVolunteer();
				volunteer.setUsername(rs.getString(1));
				volunteer.setLongitude(rs.getBigDecimal(2));
				volunteer.setLatitude(rs.getBigDecimal(3));
				list.add(i, volunteer);
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		return list;
		
	}
	
	public Users findUserByUserID(String UserID) throws SQLException{

			QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
			String sql="select * from Users  where UserID=?";
			Users user=qr.query(sql, new BeanHandler<Users>(Users.class),UserID);
		    return user;
	}
public BasicResult addLocation(String latitude,String longitude,String userID){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Users u = null;
		BasicResult	location=new BasicResult();
		try {
			conn = DBUtils.getConnection();
			String sql = "update users set longitude=?,latitude=? where UserID=?";//需要存入的 表吧（user）
			ps = conn.prepareStatement(sql);
			ps.setString(1, longitude);
			ps.setString(2, latitude);
			ps.setString(3,userID);
			System.out.print(sql);
			int a =ps.executeUpdate();
			if(a==0){
				location.setRetcode("1");
				location.setMsg("session_id无效");
				
			}else{
				location.setRetcode("0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		return location;
	}
	
public loginResult login(String userId,String pwd) {

	QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
	String sql = "select u.userName,u.sex,u.birthday,u.address,u.email,u.nation from users u where u.UserID=? and u.pwd=?";
	
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
public void updateLocation(BigDecimal lat,BigDecimal lon,String userId,Timestamp time) {
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	try {
		conn = DBUtils.getConnection();
		String sql = "update users u set u.latitude=?,u.longitude=?,u.CreateTime=? where u.UserId=?";
		ps = conn.prepareStatement(sql);
		ps.setBigDecimal(1, lat);
		ps.setBigDecimal(2, lon);
		ps.setTimestamp(3, time);
		ps.setString(4, userId);
		ps.executeUpdate();
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		DBUtils.closeALL(rs,ps, conn);
	}
	
}

public void insertUser(String UserID,String UserName,String Tel,String Sex,String MaritalStatus,String pwd,Date Birthday,String IDNo,String Address,String email,String Nation,int status,int roles,Timestamp CreateTime,String CreatorID,String CreatorName,BigDecimal longitude,BigDecimal latitude,int search) throws SQLException{
	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	String sql="insert into users(UserID,UserName,Tel,Sex,MaritalStatus,pwd,Birthday, IDNo,Address,email,Nation,status,roles,CreateTime,CreatorID,CreatorName,longitude,latitude,search) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	qr.update(sql,UserID, UserName,  Tel,Sex,MaritalStatus,pwd,Birthday, IDNo, Address,email,Nation,status,roles,CreateTime,CreatorID,CreatorName,longitude,latitude,search);
	
}
public void updateUser(String UserID,Map map) throws SQLException{
	QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	
	String str="Sex Birthday pwd IDNo email address Tel UserName";
	String[] ss=str.split(" ");
	String sql="update users set ";
	for(String s:ss){
		if(map.containsKey(s)){
			sql=sql+s+"=\""+map.get(s)+"\",";
		}
	}
	sql=sql.substring(0, sql.length()-1);
	sql+=" where UserID=\""+UserID+"\"";
	System.out.println(sql);
	qr.update(sql);
	
}
}
