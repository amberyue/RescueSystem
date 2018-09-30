package function.AED;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.DBUtils;
import bean.FindAED;


public class AEDDao {

	public List<FindAED> findAEDs(BigDecimal minlng, BigDecimal maxlng,BigDecimal minlat, BigDecimal maxlat) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FindAED> list=new ArrayList();
		try {
			conn = DBUtils.getConnection();
			String sql = "select a.latitude,a.longitude,a.address from aeds a where a.longitude>='"+minlng+"' and a.longitude<='"+maxlng+"' and a.latitude>='"+minlat+"' and a.latitude<='"+maxlat+"'";
			ps = conn.prepareStatement(sql);
			System.out.println(sql);
			rs = ps.executeQuery();
			//System.out.println(rs.next());
			int i=0;
			while (rs.next()){
				
				FindAED aed=new FindAED();
				aed.setLatitude(rs.getBigDecimal(1));
				aed.setLongitude(rs.getBigDecimal(2));
				aed.setAddress(rs.getString(3));
				list.add(i, aed);
				i++;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeALL(rs, ps, conn);
		}
		System.out.println(list);
		return list;
		
	}

	
}
