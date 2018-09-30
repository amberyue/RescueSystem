package function.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DBUtils;
import domain.Users;
import bean.BasicResult;

public class LocationDao {

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
	
	
	
		
	
}
