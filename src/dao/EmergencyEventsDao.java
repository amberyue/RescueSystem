package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Emergencyevents;
import utils.DBUtils;
import bean.FindCaller;
import bean.FindEE;

public class EmergencyEventsDao {
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
