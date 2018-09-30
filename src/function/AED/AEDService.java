package function.AED;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bean.FindAED;

public class AEDService {

	public List<FindAED> findNearbyAED(String latitude,String longitude){
		
		AEDDao aedDao=new AEDDao();
		
		Double lat= Double.parseDouble(latitude);
		Double lng= Double.parseDouble(longitude);
		
		double r=6371;	//地球半径千米 
		int i=0;
		double dis=5;//5千米距离
		
		List<FindAED> AEDs=new ArrayList<FindAED>();
		double dlng =  2 * (Math.asin(Math.sin(dis 
	                / (2 * r))  
	                / Math.cos(Math.toRadians(lat))));
		dlng=Math.abs( Math.toDegrees(dlng));
	
		double dlat=dis/r;
		dlat= dlat*180/Math.PI;
//		System.out.println("lat"+dlat+"lng"+dlng+"dlng1"+dlng1);
		AEDs = aedDao.findAEDs(new BigDecimal(lng-dlng),new BigDecimal(lng+dlng),new BigDecimal(lat-dlat),new BigDecimal(lat+dlat));
		return AEDs;
	}
}
