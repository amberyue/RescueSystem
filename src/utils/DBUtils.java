package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class DBUtils {
	private static String driverClass;  
    private static String url;
    private static String password;
    private static String username;
    
	static{
    	  ResourceBundle rb=ResourceBundle.getBundle("dbinfo"); 
    	  driverClass=rb.getString("driverClass");
    	  url=rb.getString("url");
    	  password=rb.getString("password");
    	  username=rb.getString("username");

    	  
    	  try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       
       public static  Connection getConnection() throws SQLException{
    	   return (DriverManager.getConnection(url,username,password));
       }
       public static void closeALL(ResultSet rs,PreparedStatement ps,Connection conn){
    	   if(rs!=null){
    		   try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		   rs=null;
    	   }
    	   if(ps!=null){
    		   try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		   ps=null;
    	   }
    	   if(conn!=null){
     		  try {
 				conn.close();
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
     		  conn=null;
     	  }
       }
}
