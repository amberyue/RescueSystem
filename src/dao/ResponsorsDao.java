package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import utils.C3P0Util;

public class ResponsorsDao {
			public List findResponsorsByEEID(int eeID) throws SQLException{
				 QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
				 String sql="select * from responsors where eeID=?";
				 List<Object[]> l=qr.query(sql, new ArrayListHandler(),eeID);
				 return l;
			}
			
	
}
