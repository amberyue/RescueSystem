package function.callout;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import utils.C3P0Util;
import utils.DBUtils;
import bean.FindVolunteer;
import domain.Emergencyevents;
import domain.Users;

public class CalloutDao {

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

	public void addEmergencyEvent(Emergencyevents ee){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "insert into emergencyevents(status,createTime,callerId,callerName,latitude,longitude,result,remark) Values(?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setShort(1,ee.getStatus());
			ps.setTimestamp(2,ee.getCreateTime());
			ps.setString(3,ee.getCallerId());
			ps.setString(4,ee.getCallerName());
			ps.setBigDecimal(5,new BigDecimal(ee.getLatitude()));
			ps.setBigDecimal(6,new BigDecimal(ee.getLongitude()));
			ps.setShort(7,(short) 0);
			ps.setString(8,"");
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		
	}

}
