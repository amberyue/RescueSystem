package service;

import java.sql.SQLException;

import dao.VolunteerMsgDao;

public class VolunteerMsgService {

		public void Online(String cid,String UserID) throws SQLException{
			
			VolunteerMsgDao vmDao=new VolunteerMsgDao();
			if(vmDao.findByUserID(UserID)==0){
				
				
			}
			
			
			
		}
}
