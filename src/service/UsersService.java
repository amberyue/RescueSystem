package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sun.org.apache.bcel.internal.generic.NEW;

import dao.UsersDao;

public class UsersService {
    	public void  reg(String UserID,String UserName,String Tel,Map map) throws SQLException{
    		UsersDao userDao=new UsersDao();
    		if(userDao.findUserByUserID(UserID)!=null){
    			
    			userDao.updateUser(UserID,map);
    		}else{
//    			UserID, UserName,  Tel,Sex,MaritalStatus,pwd,Birthday, IDNo, Address,email,Nation,status,roles,CreateTime,CreatorID,CreatorName,longitude,latitude,search);
    			Timestamp time=new Timestamp(System.currentTimeMillis());
    			
    			String Sex=(String) map.get("Sex");
    			Date Birthday = (Date) map.get("Birthday");
    			String IDNo=(String) map.get("IDNo");
    			
    			userDao.insertUser(UserID,UserName,Tel,Sex,"","",Birthday,IDNo,"","","",0,3,time,UserID,UserName,new BigDecimal(0),new BigDecimal(0),0);
    			
    		}
    		
    	}	
}