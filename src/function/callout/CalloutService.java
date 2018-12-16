package function.callout;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import utils.SentUtils;
import bean.FindVolunteer;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import domain.Emergencyevents;

public class CalloutService {
	CalloutDao calloutDao=new CalloutDao();
	private BigDecimal bigDecimal;
	private List<FindVolunteer> search;

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
//    private static String appId = "Bg2AVLWzgFAE1flodltUq";
    private static String appKey = "dc0e8b26fb202086084f51ad";
    private static String masterSecret = "195d97f1b34b022a670bae97";
//    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    static String CID = "";
	  //别名推送方式
	   // static String Alias = "";
//	    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	    public void search(String latitude,String longitude) throws Exception {

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

		//		 volunteerList;
		//TODO 获得clientid【这里的CID】cid：160a3797c82dbc94bd5（volunteer），传一个具体地址

			String msg=getAddress(latitude, longitude);

			 JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());

			 Collection<String> cids=new ArrayList();
			 
			    // For push, all you need do is to build PushPayload object.
			    PushPayload payload = buildPushObject_android_tag_alertWithTitle(msg,cids);

			    try {
			        PushResult result = jpushClient.sendPush(payload);
			        System.out.println("Got result - " + result);

			    } catch (APIConnectionException e) {
			        // Connection error, should retry later
			    	 System.out.println("Connection error, should retry later"+e);

			    } catch (APIRequestException e) {
			        // Should review the error, and fix the request
			    	 System.out.println("Should review the error, and fix the request"+ e);
			    	 System.out.println("HTTP Status: " + e.getStatus());
			    	 System.out.println("Error Code: " + e.getErrorCode());
			    	 System.out.println("Error Message: " + e.getErrorMessage());
			    }
	}
	    public static PushPayload buildPushObject_android_tag_alertWithTitle(String msg,Collection<String> cids) {
	    	
	    	PushPayload lllPayload= PushPayload.newBuilder()
             .setPlatform(Platform.android())
             .setAudience(Audience.all())
             .setNotification(Notification.android("呼救者在"+msg+"进行呼救", "志愿者急救提醒", null))
             .build();
	    	System.out.println(lllPayload);
	        return lllPayload;
	    }
//	    public static TransmissionTemplate transmissionTemplateDemo(String msg) {
//	        TransmissionTemplate template = new TransmissionTemplate();
//	        template.setAppId(appId);
//	        template.setAppkey(appKey);
//	    
//	        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//	        template.setTransmissionType(2);
//	        template.setTransmissionContent(msg);
//	        // 设置定时展示时间
//	        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
//	        return template;
//	    }
//	    
//	    public static LinkTemplate linkTemplateDemo(String msg) {
//	        LinkTemplate template = new LinkTemplate();
//	        // 设置APPID与APPKEY
//	        template.setAppId(appId);
//	        template.setAppkey(appKey);
//	        
//	        Style0 style = new Style0();
//	        // 设置通知栏标题与内容
//	        style.setTitle("志愿者急救提醒");
////	       TODO(推送具体信息设置）（呼救者在xxx进行呼救）
//	        style.setText("呼救者在"+msg+"进行呼救");
//	        // 配置通知栏图标
//	        style.setLogo("icon.png");
//	        // 配置通知栏网络图标
//	        style.setLogoUrl("");
//	        // 设置通知是否响铃，震动，或者可清除
//	        style.setRing(true);
//	        style.setVibrate(true);
//	        style.setClearable(true);
//	        template.setStyle(style);
//
//	        // 设置打开的网址地址
//       template.setUrl("http://www.baidu.com");
//	        return template;
//	    }
	public void addEmergencyEvent(String callerID, String latitude, String longitude, long serverTime) throws SQLException {

		Emergencyevents emergencyevents =new Emergencyevents();
		String callerName= calloutDao.findUserByUserID(callerID).getUserName();
		
		emergencyevents.setCallerId(callerID);
		emergencyevents.setCallerName(callerName);
		emergencyevents.setLatitude(Double.parseDouble(latitude));
		emergencyevents.setLongitude(Double.parseDouble(longitude));
		emergencyevents.setStatus((short)0);
		
		Timestamp createTime=new Timestamp(serverTime);
		emergencyevents.setCreateTime(createTime);
		calloutDao.addEmergencyEvent(emergencyevents);
	}

	    
    	public String getAddress(String latitude,String longitude) throws Exception{
    	String key="cfa5105bf1a223b1e050fbd3de9a62f0";
    	String url="https://restapi.amap.com/v3/geocode/regeo?key="+key+"&location="+latitude+","+longitude;
    	
    	StringBuffer s = new StringBuffer();
        s.append("key=").append(key).append("&location=").append(longitude).append(",").append(latitude);
        String res = SentUtils.sendPostData("http://restapi.amap.com/v3/geocode/regeo", s.toString());
        System.out.println("地址结果"+res);
//        logger.info(res);
        JSONObject jsonObject = JSONObject.fromObject(res);
        JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.get("regeocode"));
        String add = jsonObject1.get("formatted_address").toString();
        return add;
  
    }
}
	

