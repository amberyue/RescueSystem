package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.C3P0Util;
import domain.Contactlist;

public class ContactListDao {
	public Contactlist contactList(String UserID){
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from contactlist where UserID=?";
		try {
			List<Contactlist> contact = qr.query(sql, new BeanListHandler<Contactlist>(Contactlist.class),UserID);
			if (contact.size()!=0){
				 return contact.get(0);
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void insertContactList(String UserID,String cName,String TelNo,String RelationShip) throws SQLException{
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		String sql="insert into ContactList(UserID,cName,TelNo,RelationShip) values(?,?,?,?)";
		qr.update(sql,UserID, cName,  TelNo,RelationShip);
		
	}
	public void updateUser(String UserID,String cName,String TelNo,String RelationShip) throws SQLException{
		QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
		String sql="update ContactList set cName=?,TelNo=?,RelationShip=?  where UserID=?";
		qr.update(sql,cName,TelNo,RelationShip,UserID);
	}
}
