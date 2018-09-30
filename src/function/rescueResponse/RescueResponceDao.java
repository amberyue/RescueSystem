package function.rescueResponse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import utils.DBUtils;
import bean.FindCaller;

public class RescueResponceDao {

	public FindCaller findCaller(int emergencyID) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FindCaller caller=new FindCaller();
		try {
			conn = DBUtils.getConnection();
			String sql = "select ee.CallerName,ee.Latitude,ee.Longitude,u.sex,u.birthday,u.tel from emergencyevents ee,users u where ee.CallerID=u.UserID and ee.autoid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emergencyID);
			rs=ps.executeQuery();
//			System.out.println(rs.next());
			if(rs.next()){
				caller.setCallerName(rs.getString(1));
				caller.setLatitude(rs.getBigDecimal(2));
				caller.setLongitude(rs.getBigDecimal(3));
				caller.setSex(rs.getString(4));
				caller.setBirthday(rs.getTimestamp(5));
				caller.setTel(rs.getString(6));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		return caller;
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
}
