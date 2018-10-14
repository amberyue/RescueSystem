package dao;

import java.sql.SQLException;
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
}
