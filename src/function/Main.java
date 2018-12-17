package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.ContactListService;
import service.UsersService;
import service.VolunteerMsgService;
import utils.SHA2Utils;
import bean.BasicResult;
import bean.FindAED;
import bean.FindCaller;
import bean.loginResult;
import domain.Contactlist;
import domain.Responsor;
import function.AED.AEDService;
import function.callout.CalloutService;
import function.location.LocationDao;
import function.login.LoginDao;
import function.nearbyEE.NearbyEEService;
import function.rescueResponse.RescueResponceDao;
import function.responsor.ResponsorsService;


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
 
		System.out.println("result"+result);
		String functionId = JSONObject.fromObject(result.toString()).getString("function_id");
		String timestamp = JSONObject.fromObject(result.toString()).getString("timestamp");
		
		JSONObject data = JSONObject.fromObject(JSONObject.fromObject(result.toString()).getString("data"));
		System.out.println("data"+data);
		List<String> function = new ArrayList();
		function.add("002");
		function.add("003");
		function.add("004");
		function.add("005");
		function.add("006");
		function.add("007");
		function.add("009");
		function.add("010");
		function.add("011");
		function.add("012");
		BasicResult BasicResult = new BasicResult();
	  
		Date date = new Date();
		long time=date.getTime() ;
		
		if (functionId == null || functionId.trim() == "") {
                
		} else if (functionId.equals("001")) {

			String userId =JSONObject.fromObject(data).getString("UserID");
			String password =data.getString("pwd");
		    
			String pwd=SHA2Utils.getSHA256StrJava(password);
			// 创建session
			HttpSession session = request.getSession(true);
			String id = session.getId();
			System.out.println(id);
			session.setAttribute("userid", userId);

			LoginDao loginDao = new LoginDao();
			Timestamp servertime=new Timestamp(System.currentTimeMillis());
			System.out.println(userId);
			
			
			System.out.println(pwd);
			loginResult loginUser = loginDao.login(userId, pwd);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("retcode", loginUser.getRetcode());
			jsonObject.put("msg", loginUser.getMsg());
			JSONObject returnData = new JSONObject();
			
			if(loginUser.getUser()!=null){
				String ip = getRemoteHost(request);
				loginDao.loginRecord(userId,loginUser.getUser().getUserName(),ip,servertime);
				returnData.put("session_id", id);
				returnData.put("username",loginUser.getUser().getUserName());
				Date birthday = loginUser.getUser().getBirthday();
				returnData.put("birthday",loginUser.getUser().getBirthday().toString());
				returnData.put("sex",loginUser.getUser().getSex());
				returnData.put("email",loginUser.getUser().getEmail());
				returnData.put("address",loginUser.getUser().getAddress());
				returnData.put("nation",loginUser.getUser().getNation());
				returnData.put("tel",loginUser.getUser().getTel());
				returnData.put("address",loginUser.getUser().getAddress());
				returnData.put("pwd",loginUser.getUser().getPwd());
				returnData.put("UserID", userId);
				
				Contactlist contactList = loginDao.contactList(userId);
				if (contactList!=null){
					  JSONObject contact=new JSONObject();
				      contact.put("cName", contactList.getCname());
				      contact.put("TelNo", contactList.getTelNo());
				      contact.put("Relationship", contactList.getRelationShip());
				      returnData.put("ContactList",contact);
				}
			}
			jsonObject.put("data", returnData);
			response.setContentType("text/json; charset=utf-8");
			response.getWriter().print(jsonObject);

		} else if (function.contains(functionId)) {
			String session_id = JSONObject.fromObject(result.toString()).getString(
					"session_id");
			System.out.println("login产生的sessionid"+session_id);
			
		    String sessionID=request.getSession().getId();
		    System.out.println("请求的sessionid"+sessionID);
			if ( session_id==null||session_id=="")
			{
				System.out.println("没有sessionid");
				BasicResult.setRetcode("-9");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("retcode", BasicResult.getRetcode());
				response.setContentType("text/json; charset=utf-8");
				response.getWriter().print(jsonObject);
			} else {
				long serverTime = System.currentTimeMillis();
				if ((serverTime - time) > 3600000) {
					System.out.println("超时");
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

						{
							String userID = (String) request.getSession(false).getAttribute("userid");
							System.out.println(userID);
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

						{
							String CallerID = (String) request
									.getSession(false).getAttribute("userid");
						
							CalloutService calloutService = new CalloutService();
							
							 try {
								
								calloutService.search(latitude, longitude);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
//							 List<FindVolunteer> volunteer =
//								try {
//									calloutService.addEmergencyEvent(CallerID,
//											latitude, longitude, serverTime);
//								} catch (SQLException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
						
							BasicResult.setRetcode("0");
							BasicResult.setMsg("");
							List list = new ArrayList();
							int i = 0;
//							for (FindVolunteer volunteer : volunteers) {
//
//								Map<String, String> map4 = new HashMap();
//								map4.put("name", volunteer.getUsername());
//								map4.put("latitude", volunteer.getLatitude()
//										.toString());
//								map4.put("longitude", volunteer.getLongitude()
//										.toString());
//
//								list.add(i, map4);
//								i++;
//							}
							jsonObject.put("retcode", BasicResult.getRetcode());
							jsonObject.put("msg", BasicResult.getMsg());
//							JSONArray returnData = JSONArray.fromObject(list);
							jsonObject.element("data", "");
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
					case 7:{
						String cid=data.getString("cid");
						String UserID=data.getString("UserID");
						
						VolunteerMsgService vMsgService=new VolunteerMsgService();
//						vMsgService.Online(cid,UserID);
						
						
						
						break;
						
					}
					case 9:{
						 int eeID=Integer.parseInt( data.getString("eeID"));
						 System.out.println(eeID);
						 ResponsorsService responsorsService = new ResponsorsService();
						 try {
							JSONObject jsonObject = new JSONObject();
							List<Responsor> findResponsors = responsorsService.findResponsors(eeID);
							BasicResult.setRetcode("0");
							BasicResult.setMsg("");
							BasicResult.setData(data);
							jsonObject.put("retcode", "0");
							jsonObject.put("msg", "");
							JSONArray returnData=JSONArray.fromObject(findResponsors);
							jsonObject.element("data",returnData);
							response.setContentType("text/json; charset=utf-8");
							response.getWriter().print(jsonObject);
							break; 
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					case 10:{
//					String UserID,String UserName,char Sex,Date Birthday,String Tel,String IDNo
						
						String UserID= data.getString("UserID");
						String UserName=data.getString("UserName");
						String Tel=data.getString("Tel");
						
//						String Birthday=data.getString("Birthday");
						String Sex = "";
						String IDNo="";
						String birthday;
						Date Birthday=null;
						String pwd="";
						String email = "";
						String address = "";
						String str="1996-01-05";
						
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("User Name", UserName);
						map.put("Tel", Tel);
						System.out.println("包不包含sex"+data.containsKey("Sex"));
						System.out.println("包不包含pwd"+data.containsKey("pwd"));
						if(data.containsKey("Sex")){
							
							Sex=data.getString("Sex");
							IDNo=data.getString("IDNo");
							
							birthday=data.getString("Birthday");
							System.out.println("birthday     ："+birthday);
	
							map.put("Sex", Sex);
							map.put("IDNo", IDNo);
							map.put("Birthday", birthday);
						}
						
						if(data.containsKey("pwd")){
							pwd=data.getString("pwd");
							email=data.getString("email");
							address=data.getString("address");
							map.put("pwd", pwd);
							map.put("email", email);
							map.put(" ", address);
						}
						
						for (Entry<String, Object> key : map.entrySet()) { 
							  System.out.println("Key = " + key); 
							} 
				
						try {
							
							UsersService usersService=new UsersService();
							usersService.reg(UserID, UserName,Tel,map);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						if(data.containsKey("cName")){
							String cName = data.getString("cName");
							String TelNo = data.getString("TelNo");
							String RelationShip = data.getString("RelationShip");
							ContactListService contactListService = new ContactListService();
							try {
								contactListService.ContactListAddOrUpdate(UserID, cName, TelNo, RelationShip);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					
						JSONObject jsonObject=new JSONObject();
						jsonObject.put("retcode", 0);
						jsonObject.put("msg","");
						JSONObject returnData = new JSONObject();
						jsonObject.put("data", returnData);
					
						response.setContentType("text/json; charset=utf-8");
						response.getWriter().print(jsonObject);
						break;
					}
//					 
					case 12:{
						String UserID=data.getString("UserID");
						ContactListService contactListService = new ContactListService();
						Contactlist sentMessage = contactListService.sentMessage(UserID);
						JSONObject jsonObject=new JSONObject();
						jsonObject.put("retcode", 0);
						jsonObject.put("msg","");
						JSONObject returnData=new JSONObject();
						
						JSONArray jsonArray = new JSONArray();
						int sent=0;
					    if(sentMessage!=null){
					    	sent=1;
					    	jsonArray.add(0, sentMessage.getCname());
					    	jsonArray.add(1,sentMessage.getTelNo());
					    	jsonArray.add(2,sentMessage.getRelationShip());
							returnData.accumulate("sentMessage", jsonArray);
					    }
					    returnData.put("sent", sent);
					    jsonObject.put("data",returnData);
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

	

}
