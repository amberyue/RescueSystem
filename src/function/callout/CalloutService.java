package function.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.FindVolunteer;
import domain.Emergencyevents;

public class CalloutService {
	
	CalloutDao calloutDao=new CalloutDao();
	private BigDecimal bigDecimal;
	private List<FindVolunteer> search;
	
	public List<FindVolunteer> search(String latitude,String longitude) {
	//	System.out.println("lat"+latitude+"lng"+longitude);
		Double lat= Double.parseDouble(latitude);
		Double lng= Double.parseDouble(longitude);
		double r=6371;	//地球半径千米 
		int i=0;
		double dis=0.5;//0.5千米距离
		List<FindVolunteer> volunteerList=new ArrayList(); 
		
		while(i<3){
			
			double dlng = 2*Math.asin(Math.sin(dis/(2*r)))/Math.cos(lat*Math.PI/180);
			dlng = Math.abs(dlng*180/Math.PI);
			double dlat=dis/r;
			dlat= dlat*180/Math.PI;
			System.out.println("lat"+dlat+"lng"+dlng);
			volunteerList = calloutDao.search(new BigDecimal(lng-dlng),new BigDecimal(lng+dlng),new BigDecimal(lat-dlat),new BigDecimal(lat+dlat));
			System.out.print(volunteerList);
			i=volunteerList.size();
			dis=dis+0.5;
		}
		
		return volunteerList;
	}

	public void addEmergencyEvent(String callerID, String latitude, String longitude, long serverTime) {

		Emergencyevents emergencyevents =new Emergencyevents();
		String callerName = calloutDao.findCallerName(callerID);
		emergencyevents.setCallerId(callerID);
		emergencyevents.setCallerName(callerName);
		emergencyevents.setLatitude(Double.parseDouble(latitude));
		emergencyevents.setLongitude(Double.parseDouble(longitude));
		emergencyevents.setStatus((short)0);
		
		Timestamp createTime=new Timestamp(serverTime);
		emergencyevents.setCreateTime(createTime);
		calloutDao.addEmergencyEvent(emergencyevents);
	}

	
	
}
