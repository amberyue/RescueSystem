package function.nearbyEE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.FindEE;

public class NearbyEEService {
 
	public List<Map<String, Object>> findNearEE(double latitude,double longitude){
		NearbyEEDao nbDao=new NearbyEEDao();
		List<FindEE> EEs = nbDao.findEE();
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		for(FindEE EE: EEs){
			
			Map<String,Object> map=new HashMap<String,Object>();
			double distance = getDistance(EE.getLatitude().doubleValue(),EE.getLongitude().doubleValue(),latitude,longitude);
			map.put("distance", distance);
			map.put("CallerName",EE.getCallerName());
			map.put("CreateTime", EE.getCreateTime());
			map.put("latitude", EE.getLatitude());
			map.put("longitude",EE.getLongitude());
			list.add(map);
		}
	
//		Collections.sort(list, new Comparator<Map<String,Object>>() {
//			
//			public int compare(Map<String,Object> firstMapEntry,   
//                     Map<String,Object> secondMapEntry) {  
//				return ((String) firstMapEntry.get("distance")).compareTo((String) secondMapEntry.get("distance"));               
//                int  distance1=(Integer) firstMapEntry.get("distance");  
//                  
//                int  distance2=(Integer) secondMapEntry.get("distance");  
//            return  (distance1>distance2?1:-1);  
//          }  
//		
//		});
		
		Collections.sort(list, new Comparator<Map<String, Object>>(){
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {

				double distance1 = (Double) o1.get("distance");
				double distance2 = (Double) o2.get("distance");
				return (distance1 > distance2 ? 1 : -1);
			}});
		
		List<Map<String, Object>> listResult=new ArrayList<Map<String, Object>>();
		if(list.size()>=4)
			    listResult=list.subList(0, 4);
		else{
				listResult=list.subList(0,list.size());
		}
	//	System.out.println(listResult);
		return listResult;
	}
	private static double EARTH_RADIUS = 6378.137;    
    
    private static double rad(double d) {    
        return d * Math.PI / 180.0;    
    }    
    
    /**   
     * 通过经纬度获取距离(单位：米)   
     * @param lat1   
     * @param lng1   
     * @param lat2   
     * @param lng2   
     * @return   
     */    
    public static double getDistance(double lat1, double lng1, double lat2,    
                                     double lng2) {    
        double radLat1 = rad(lat1);    
        double radLat2 = rad(lat2);    
        double a = radLat1 - radLat2;    
        double b = rad(lng1) - rad(lng2);    
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)    
                + Math.cos(radLat1) * Math.cos(radLat2)    
                * Math.pow(Math.sin(b / 2), 2)));    
        s = s * EARTH_RADIUS;    
        s = Math.round(s * 10000d) / 10000d;    
        s = s*1000;    
        return s;    
    }    
}    
