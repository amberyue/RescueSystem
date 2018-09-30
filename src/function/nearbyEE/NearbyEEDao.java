package function.nearbyEE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.DBUtils;
import bean.FindCaller;
import bean.FindEE;

public class NearbyEEDao {

	public List<FindEE> findEE() {
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FindEE> list=new ArrayList<FindEE>();
		try {
			conn = DBUtils.getConnection();
			String sql = "select CallerName,Latitude,Longitude,CreateTime from emergencyevents ee where ee.status=0 OR ee.status=1";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int i=0;
			while(rs.next()){
				FindEE findEE=new FindEE();
				findEE.setCallerName(rs.getString(1));
				findEE.setLatitude(rs.getBigDecimal(2));
				findEE.setLongitude(rs.getBigDecimal(3));
				findEE.setCreateTime(rs.getTimestamp(4));
				list.add(i, findEE);
				i++;
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		return null;
	}
}