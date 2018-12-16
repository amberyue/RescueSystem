package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domain.Users;
import utils.C3P0Util;
import utils.DBUtils;
import bean.BasicResult;
import bean.VolunteerMsg;


public class VolunteerMsgDao {
	public int findByUserID(String UserID) throws SQLException{

		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from VolunteerMsg  where UserID=?";
		int s=qr.query(sql, new BeanListHandler<VolunteerMsg>(VolunteerMsg.class),UserID).size();
	    return s;
	}
	
public BasicResult addLocation(String UserID,String cid){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Users u = null;
		BasicResult	location=new BasicResult();
		try {
			conn = DBUtils.getConnection();
			String sql = "update users set longitude=?,latitude=? where UserID=?";//需要存入的 表吧（user）
			ps = conn.prepareStatement(sql);
//			ps.setString(1, longitude);
//			ps.setString(2, latitude);
//			ps.setString(3,userID);
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
