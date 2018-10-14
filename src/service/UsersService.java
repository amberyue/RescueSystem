package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sun.org.apache.bcel.internal.generic.NEW;

import dao.UsersDao;

public class UsersService {
    	public void  reg(String UserID,String UserName,String Sex,Date Birthday,String Tel,String IDNo) throws SQLException{
    		UsersDao userDao=new UsersDao();
    		if(userDao.findUserByUserID(UserID)!=null){
    			userDao.updateUser(UserID,UserName,Sex,Birthday,Tel,IDNo);
    		}else{
//    			UserID, UserName,  Tel,Sex,MaritalStatus,pwd,Birthday, IDNo, Address,email,Nation,status,roles,CreateTime,CreatorID,CreatorName,longitude,latitude,search);
    			
    			Timestamp time=new Timestamp(System.currentTimeMillis());
    			userDao.insertUser(UserID,UserName,Tel,Sex,"","",Birthday,IDNo,"","","",0,3,time,UserID,UserName,new BigDecimal(0),new BigDecimal(0),0);
    			
    		}
    		
    	}	
}