package com.ty.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ty.dao.UserDao;
import com.ty.entity.User;
import com.ty.util.JsUtil;

/**
 * 涓昏鏄敤鎴风鐞嗙殑閫昏緫鍔熻兘
 * @author Administrator
 *
 */
@WebServlet(urlPatterns="/admin/user") //鏄犲皠璇锋眰璁块棶璺緞
public class UserController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //璁剧疆缂栫爜鏍煎紡
		//1.閫氳繃璇锋眰鍙傛暟鍒ゆ柇褰撳墠澶勭悊鐨勭被鍨�
		String action=req.getParameter("action");
		//榛樿鐨勫鐞嗙被鍨�鍒ゆ柇鏄惁浣跨敤榛樿绫诲瀷锛岃繕鏄娇鐢ㄥ綋鍓嶆祻瑙堝櫒浼犻�鐨勫弬鏁�
		action=action==null||"".equals(action)?"list":action;
		
		if("list".equals(action)){ //鏌ヨ鎿嶄綔
			list(req,resp);
		}else if("add".equals(action)){ //娣诲姞鎿嶄綔
			add(req,resp);
		}else if("update".equals(action)){ //缂栬緫鎿嶄綔
			
		}
		
		
	}

	 //鏂板鎿嶄綔
	private void add(HttpServletRequest req, HttpServletResponse resp) {
		//鎺ュ彈鐢ㄦ埛鎻愪氦鐨勬暟鎹�
		String userCode=req.getParameter("userCode"); //鐢ㄦ埛缂栫爜
		String userName=req.getParameter("userName"); //鐢ㄦ埛鍚嶇О
		String userPassword=req.getParameter("userPassword"); //瀵嗙爜
		String ruserPassword=req.getParameter("ruserPassword"); //纭瀵嗙爜
		String gender=req.getParameter("gender"); //鎬у埆
		String birthday=req.getParameter("birthday"); //鐢熸棩
		String phone=req.getParameter("phone"); //鐢佃瘽鍙风爜
		String address=req.getParameter("address"); //鍦板潃
		String userRole=req.getParameter("userRole"); //瑙掕壊缂栧彿
		
		User user=new User(); //瀛樺偍鐢ㄦ埛鎻愪氦鐨勬暟鎹�
		user.setAddress(address); //鍦板潃
		
		User loginUser=(User)req.getSession().getAttribute("user"); //鑾峰彇褰撳墠鐨勭櫥褰曠敤鎴峰璞�
		//鏈夐偅涓櫥褰曠殑鐢ㄦ埛鎻掑叆鐨�
		user.setCreatedBy(loginUser.getId()); //鍒涘缓鑰呯紪鍙�
		user.setCreationDate(new Date()); //鍒涘缓鏃堕棿
		user.setGender(Integer.parseInt(gender)); //鎬у埆
		user.setPhone(phone);  //鎵嬫満鍙风爜
		user.setUserCode(userCode); //鐢ㄦ埛缂栫爜
		user.setUserName(userName); //鐢ㄦ埛濮撳悕
		user.setUserPassword(ruserPassword); //瀵嗙爜
		user.setUserRole(Integer.parseInt(userRole)); //瑙掕壊缂栧彿
		
		
		
		//1.鍒ゆ柇鎻愪氦鐨勬暟鎹槸鍚︿负绌�
		 if("".equals(userCode)){
			 JsUtil.printAlert(resp, "鐢ㄦ埛缂栫爜涓嶈兘涓虹┖锛�");
			 return;
		 }
		 if("".equals(userName)){
			 JsUtil.printAlert(resp, "鐢ㄦ埛鍚嶄笉鑳戒负绌猴紒");
			 return;
		 }
		 
		 if("".equals(userPassword)){
			 JsUtil.printAlert(resp, "瀵嗙爜涓嶈兘涓虹┖锛�");
			 return;
		 }
		 
		 if(!userPassword.equals(ruserPassword)){
			 JsUtil.printAlert(resp, "涓や釜瀵嗙爜涓嶄竴鑷达紒");
			 return;
		 }
		         
		 if("".equals(birthday)){
			 JsUtil.printAlert(resp, "璇烽�鎷╃敓鏃ユ椂闂达紒");
			 return;
		 }
		 
		//瀛楃涓茶浆鎹㈡椂闂寸被鍨嬶紝蹇呴』浣跨敤SimpleDateFormat
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			user.setBirthday(sdf.parse(birthday));
		} catch (ParseException e1) {
			// TODO 鑷姩鐢熸垚鐨�catch 鍧�
			e1.printStackTrace();
		} //鐢熸棩
			
		//2.褰撳墠鐨勭敤鎴风紪鐮佸湪鏁版嵁搴撲腑鏄惁瀛樺湪
		UserDao userDao=new UserDao();
		try {
			int count=userDao.exist(userCode);
			if(count>=1){
				JsUtil.printAlert(resp, "褰撳墠鐨勭敤鎴风紪鐮佸凡缁忓瓨鍦紝璇烽噸鏂拌緭鍏ワ紒");
				return;
			}
		} catch (Exception e) {
			// TODO 鑷姩鐢熸垚鐨�catch 鍧�
			e.printStackTrace();
		} //璇诲彇鏁版嵁搴撲腑褰撳墠鐢ㄦ埛缂栫爜鏄惁瀛樺湪
		
		//3.鎻掑叆鍒版暟鎹簱涓�
		try {
			int rowcount=userDao.insert(user);
			if(rowcount>=1){
				JsUtil.printAlert(resp, "淇濆瓨鎴愬姛锛�");
			}else{
				JsUtil.printAlert(resp, "淇濆瓨澶辫触锛�");
			}
		} catch (Exception e) {
			// TODO 鑷姩鐢熸垚鐨�catch 鍧�
			e.printStackTrace();
		}
	}
	
	//鍒楄〃鐨勬搷浣�
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		//鎺ュ彈娴忚鍣ㄤ紶閫掕繃鏉ョ殑鐢ㄦ埛鍚�
		String queryName=req.getParameter("queryname");
		String queryUserRole=req.getParameter("queryUserRole");
		
		 //1.璋冪敤UserDao
		UserDao userDao=new UserDao();
		//2.璇诲彇鎵�湁鐨勭敤鎴疯〃鏁版嵁
	    try {
			List<User> userDataList=userDao.selectAll(queryName,queryUserRole);
			req.setAttribute("userDataList", userDataList); //灏嗗�浼犻�鍒伴〉闈�
			//灏嗛泦鍚堢殑鏁版嵁杩斿洖鍒皍serlist.jsp ,璺宠浆
			req.getRequestDispatcher("userlist.jsp").forward(req, resp);
		} catch (Exception e) {
			// TODO 鑷姩鐢熸垚鐨�catch 鍧�
			e.printStackTrace();
		}
	}


}
