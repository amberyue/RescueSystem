package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import bean.BasicResult;
import bean.FindAED;
import bean.FindCaller;
import bean.FindVolunteer;
import bean.loginResult;
import function.AED.AEDService;
import function.callout.CalloutService;
import function.location.LocationDao;
import function.login.LoginDao;
import function.nearbyEE.NearbyEEService;
import function.rescueResponse.RescueResponceDao;

public class Main extends HttpServlet {

	public static final String ERROR_MSG = "session_id无效";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8"); // 返回页面防止出现中文乱码
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));// post方式传递读取字符流
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
		try {
			while ((jsonStr = reader.readLine()) != null) {
				result.append(jsonStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader.close();// 关闭输入流

		String functionId = JSONObject.fromObject(result.toString()).getString(
				"function_id");
		String timestamp = JSONObject.fromObject(result.toString()).getString("timestamp");
		
		String digest = JSONObject.fromObject(result.toString()).getString("digest");
		JSONObject data = JSONObject.fromObject(JSONObject.fromObject(result.toString()).getString("data"));
		System.out.println(data);
		List<String> function = new ArrayList();
		function.add("002");
		function.add("003");
		function.add("004");
		function.add("005");
		function.add("006");
		BasicResult BasicResult = new BasicResult();
		SimpleDateFormat s = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		long time = (long) 0;
		try {
			time = s.parse(timestamp).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (functionId == null || functionId.trim() == "") {
                
		} else if (functionId.equals("001")) {

			String userId =JSONObject.fromObject(data).getString("UserID");
			String pwd =data.getString("pwd");
			// 创建session
			HttpSession session = request.getSession(true);
			String id = session.getId();
			System.out.print(id);
			session.setAttribute("userid", userId);

			LoginDao loginDao = new LoginDao();
			Timestamp servertime=new Timestamp(System.currentTimeMillis());
			loginResult loginUser = loginDao.login(userId, pwd, id);
			
			String ip = getRemoteHost(request);
			loginDao.loginRecord(userId,loginUser.getUserName(),ip,servertime);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("retcode", loginUser.getRetcode());
			jsonObject.put("msg", loginUser.getMsg());

			JSONObject returnData = new JSONObject();
			returnData.put("session_id", loginUser.getSessionid());
			returnData.put("username",loginUser.getUserName());
			Date birthday = loginUser.getBirthday();
			
			returnData.put("birthday",loginUser.getBirthday().toString());
			returnData.put("sex",loginUser.getSex());
			returnData.put("email",loginUser.getEmail());
			returnData.put("address",loginUser.getAddress());
			returnData.put("nation",loginUser.getNation());
			jsonObject.put("data", returnData);

			response.setContentType("text/json; charset=utf-8");
			response.getWriter().print(jsonObject);

		} else if (function.contains(functionId)) {
			String session_id = JSONObject.fromObject(result.toString()).getString(
					"session_id");
		    String sessionID=request.getSession().getId();
			if (null == request.getSession(false)||!(sessionID.equals(session_id)))
			{
				BasicResult.setRetcode("-9");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("retcode", BasicResult.getRetcode());
				response.setContentType("text/json; charset=utf-8");
				response.getWriter().print(jsonObject);
			} else {
				long serverTime = System.currentTimeMillis();
				if ((serverTime - time) > 3600000) {

					BasicResult.setRetcode("1");
					BasicResult.setMsg(ERROR_MSG);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("retcode", BasicResult.getRetcode());
					jsonObject.put("msg",BasicResult.getMsg());

					JSONObject returnData = new JSONObject();
					jsonObject.put("data", returnData);
					
					response.setContentType("text/json; charset=utf-8");
					response.getWriter().print(jsonObject);
				} else {

					String check = session_id + timestamp + "CPR";
//System.out.println(check);
					int functionid = Integer.parseInt(functionId);
					switch (functionid) {
					case 2: {
						String latitude =data.getString("latitude");
						System.out.println(latitude);
						String longitude =data.getString("longitude");

						check = check + latitude + longitude;

						JSONObject jsonObject = new JSONObject();
//						if (digest != SHA2Utils.getSHA256StrJava(check)) {
//							BasicResult.setRetcode("1");
//							
//						} else 
						{
							String userID = (String) request.getSession(false).getAttribute("userid");
							System.out.print(userID);
							LocationDao locationDao = new LocationDao();
							BasicResult = locationDao.addLocation(latitude,
									longitude,userID);
						}
							jsonObject.put("retcode", BasicResult.getRetcode());
							jsonObject.put("msg", BasicResult.getMsg());
							JSONObject returnData = new JSONObject();
							jsonObject.put("data", returnData);
						
						response.setContentType("text/json; charset=utf-8");
						response.getWriter().print(jsonObject);
						break;
					}

					case 3: {
						String latitude =data.getString("latitude");
						String longitude =data.getString("longitude");

						check = check + latitude + longitude;
						JSONObject jsonObject = new JSONObject();
//						if (digest != SHA2Utils.getSHA256StrJava(check)) {
//							BasicResult.setRetcode("1");
//						} else 
						{
							String CallerID = (String) request
									.getSession(false).getAttribute("userid");
							System.out.print(CallerID);
							CalloutService calloutService = new CalloutService();

							List<FindVolunteer> volunteers = calloutService
									.search(latitude, longitude);
							calloutService.addEmergencyEvent(CallerID,
									latitude, longitude, serverTime);
							BasicResult.setRetcode("0");
							BasicResult.setMsg("");
							List list = new ArrayList();
							int i = 0;
							for (FindVolunteer volunteer : volunteers) {

								Map<String, String> map4 = new HashMap();
								map4.put("name", volunteer.getUsername());
								map4.put("latitude", volunteer.getLatitude()
										.toString());
								map4.put("longitude", volunteer.getLongitude()
										.toString());

								list.add(i, map4);
								i++;
							}
							jsonObject.put("retcode", BasicResult.getRetcode());
							jsonObject.put("msg", BasicResult.getMsg());
							JSONArray returnData = JSONArray.fromObject(list);
							jsonObject.element("data", returnData);
						}
						response.setContentType("text/json; charset=utf-8");
						response.getWriter().print(jsonObject);
						break;
					}
					case 4: {
						String emergencyID =data.getString("emergency_id");
						String latitude =data.getString("latitude");
						String longitude =data.getString("longitude");
						
						check = check + emergencyID + latitude + longitude;
						JSONObject jsonObject = new JSONObject();
//						if (digest != SHA2Utils.getSHA256StrJava(check)) {
//							BasicResult.setRetcode("1");
//
//						} else
						{
							RescueResponceDao rrDao = new RescueResponceDao();

							Timestamp time1 = new Timestamp(serverTime);
							String userId = (String) request.getSession(false)
									.getAttribute("userid");
							BigDecimal lat = new BigDecimal(latitude);
							BigDecimal lon = new BigDecimal(longitude);
							rrDao.updateLocation(lat, lon, userId, time1);
							
							System.out.println("id:"+Integer
									.parseInt(emergencyID));
							FindCaller caller = rrDao.findCaller(Integer
									.parseInt(emergencyID));
							
							BasicResult.setRetcode("0");
							BasicResult.setMsg("");

							jsonObject.put("retcode", BasicResult.getRetcode());
							jsonObject.put("msg", BasicResult.getMsg());
							jsonObject.put("data", caller);//添加对象。。
							
						}
						response.setContentType("text/json; charset=utf-8");
						response.getWriter().print(jsonObject);
						break;
					}
					case 5: {

						String latitude =data.getString("latitude");
						String longitude =data.getString("longitude");

						check = check + latitude + longitude;
						JSONObject jsonObject = new JSONObject();
//						if (digest != SHA2Utils.getSHA256StrJava(check)) {
//							BasicResult.setRetcode("1");
//
//						} else
						{
							NearbyEEService nearbyEEService = new NearbyEEService();
							List<Map<String, Object>> findNearEE = nearbyEEService
									.findNearEE(Double.parseDouble(latitude),
											Double.parseDouble(longitude));
							
					//		System.out.println(findNearEE);
							BasicResult.setRetcode("0");
							BasicResult.setMsg("");

							jsonObject.put("retcode", BasicResult.getRetcode());
							jsonObject.put("msg", BasicResult.getMsg());
							JSONArray returnData = new JSONArray();
							int i=0;
							for(Map<String, Object> list: findNearEE){
								returnData.add(i, list);
								i++;
							}
							jsonObject.element("data", returnData);
							
						}
						response.setContentType("text/json; charset=utf-8");
						response.getWriter().print(jsonObject);
						break;
					}
					case 6: {
						String latitude =data.getString("latitude");
						String longitude =data.getString("longitude");

						check = check + latitude + longitude;
						JSONObject jsonObject = new JSONObject();
//						if (digest != SHA2Utils.getSHA256StrJava(check)) {
//							BasicResult.setRetcode("1");
//
//						} else
						{
							AEDService aedService = new AEDService();
							List<FindAED> findNearbyAEDs = aedService
									.findNearbyAED(latitude, longitude);

							BasicResult.setRetcode("0");
							BasicResult.setMsg("");
							System.out.println(findNearbyAEDs);
							JSONArray returnData=JSONArray.fromObject(findNearbyAEDs);
							jsonObject.element("data",returnData);
						}
						response.setContentType("text/json; charset=utf-8");
						response.getWriter().print(jsonObject);
						break;
					  }
					}
				}
			}
		}
	}
	
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
		    
			String ip = request.getHeader("x-forwarded-for");
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getRemoteAddr();
		    }
		    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		}
	 //request.getSession(false)==null可以近似的判断是否过期：如果已经过期，那么返回的是null，但是当起一次请求，刚刚建立一个session的时候，上述方法也返回null
	 //所以应该这个做
	 // if(null==request.getSession(false)){
	 // if(true==request.getSession(true).isNew()){
	 // }
	 // else{
	 // System.out.println("session已经过期");
	 // }
	 // }
	// }
	// default:{
	//
	// }

//	private static JSONObject createJSONArray() {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("function_id", "001");
//		jsonObject.put("timestamp", "23233322");
//		jsonObject.put("digest", "ASFLKSJFJSDLFJS");
//		jsonObject.put("Min.score", new Integer(99));
//
//		// 输出jsonobject对象
//		// System.out.println("jsonObject==>"+jsonObject);
//
//		// 判读输出对象的类型
//		boolean isArray = jsonObject.isArray();
//		boolean isEmpty = jsonObject.isEmpty();
//		boolean isNullObject = jsonObject.isNullObject();
//		// System.out.println("isArray:"+isArray+" isEmpty:"+isEmpty+" isNullObject:"+isNullObject);
//
//		// 添加属性
//		jsonObject.element("address", "福建省厦门市");
//		// System.out.println("添加属性后的对象==>"+jsonObject);
//
//		// 返回一个JSONArray对象
//		JSONArray jsonArray = new JSONArray();
//		List<Map<String, Object>> list = new ArrayList();
//
//		Map<String, Object> map1 = new HashMap();
//		Map<String, Object> map2 = new HashMap();
//		map1.put("UserID", "sys001");
//		map1.put("aa", "a");
//		map2.put("pwd", "sdfsfsfdsdfsd");
//		list.add(0, map1);
//		list.add(1, map2);
//		jsonArray.add(0, list.get(0));
//		jsonArray.add(1, list.get(1));
//
//		jsonObject.element("jsonArray", jsonArray);
//		JSONArray array = jsonObject.getJSONArray("jsonArray");
//		return jsonObject;
//	}
//
//	public static void main(String[] args) {
//		JSONObject array = createJSONArray();
//
//		Map<String, Object> map = (Map<String, Object>) (JSONArray
//				.fromObject(JSONObject.fromObject(array).getString("jsonArray")))
//				.get(0);
//	//	String s = (String) map.get("UserID");
//		System.out.println(array);
//
//	}
	

}
