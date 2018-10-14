package dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.dbutils.QueryRunner;

import utils.C3P0Util;

public class LoginsDao {
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
