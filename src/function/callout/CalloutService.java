package function.callout;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.FindVolunteer;
import domain.Emergencyevents;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CalloutService {
	
	CalloutDao calloutDao=new CalloutDao();
	private BigDecimal bigDecimal;
	private List<FindVolunteer> search;

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "JUJv2z3uznAwoNsnON9b39";
    private static String appKey = "sLHEuEhPLi6l0QbtGWKAl2";
    private static String masterSecret = "Me2oiI72Ju53cAlCO3z3V9";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

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

	    public void tuisong() throws IOException {

	        IGtPush push = new IGtPush(url, appKey, masterSecret);

	        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
	        LinkTemplate template = new LinkTemplate();
	        template.setAppId(appId);
	        template.setAppkey(appKey);
	        template.setTitle("请填写通知标题");
	        template.setText("请填写通知内容");
	        template.setUrl("http://getui.com");

	        List<String> appIds = new ArrayList<String>();
	        appIds.add(appId);

	        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
	        AppMessage message = new AppMessage();
	        message.setData(template);
	        message.setAppIdList(appIds);
	        message.setOffline(true);
	        message.setOfflineExpireTime(1000 * 600);

	        IPushResult ret = push.pushMessageToApp(message);
	        System.out.println(ret.getResponse().toString());
	    }
	

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

	
	
	
}
