package com.ty.controller;

import java.io.IOException;  

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ty.dao.RoleDao;
import com.ty.dao.UserDao;
import com.ty.entity.Role;
import com.ty.entity.User;
import com.ty.util.JsUtil;

@WebServlet(urlPatterns="/public")
public class PublicController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//鐧婚檰鍜岄�鍑轰娇鐢ㄤ竴涓猻ervlet锛燂紵
		//璁块棶servlet鏃跺�浼犻�杩囨潵涓�釜绫诲瀷锛屾潵鍖哄垎鎵ц閭ｅ潡浠ｇ爜
		String action=req.getParameter("action"); //鑾峰彇椤甸潰鐨勭被鍨�
		action=action==null||"".equals(action)?"login":action; //鍒ゆ柇绫诲瀷涓虹┖锛屽鏋滅瓑浜庣┖鍊硷紝榛樿鎯呭喌璋冪敤鐧婚檰鐨勬柟娉曪紝涓夌洰杩愮畻绗�  鎺ュ彈鍙橀噺=鏉′欢?鍊�鍊�
		
		if("login".equals(action)){ //鐧婚檰鎿嶄綔
			login(req,resp);
		}else if("out".equals(action)){ //閫�嚭鎿嶄綔
			out(req,resp);
		}
		
	}
	
	//閫�嚭
	public void out(HttpServletRequest req, HttpServletResponse resp){
		 //1.绉婚櫎session瀛樺偍user淇℃伅
		 req.getSession().removeAttribute("user");
		 //2.璺宠浆鍒扮櫥闄嗛〉闈�
		 try {
			resp.sendRedirect("login.jsp");
		} catch (IOException e) {
			// TODO 鑷姩鐢熸垚鐨�catch 鍧�
			e.printStackTrace();
		}
	}
	
	//鐧婚檰鐨勬柟娉�
	public void login(HttpServletRequest req, HttpServletResponse resp){
		//閫氳繃request瀵硅薄涓璯etParameter鑾峰彇娴忚鍣ㄤ紶閫掔殑鏁版嵁,闇�浼犻�琛ㄥ崟鐨勫悕绉板悕绉帮紝鎵嶈兘鑾峰彇瀵瑰簲鐨勫�
		String userCode=req.getParameter("userCode"); //鎺ユ敹鐢ㄦ埛璐﹀彿
		String userPassword=req.getParameter("userPassword"); //鑾峰彇瀵嗙爜
		String code=req.getParameter("code"); //鑾峰彇椤甸潰鐨勯獙璇佺爜
		//1.鍒ゆ柇楠岃瘉鐮佹槸鍚︽纭�闂锛氭�涔堣幏鍙栧浘鐗囦腑楠岃瘉鐮佺殑鍊硷紝灏嗛獙璇佺爜瀛樻斁鍦╯ession瀵硅薄锛屽氨鑳戒繚瀛樺浘鐗囩殑楠岃瘉鐮�
		String tempCode=(String)req.getSession().getAttribute("allCode");
		System.out.println(userCode);
		System.out.println(userPassword);
		System.out.println(code);
		System.out.println(tempCode);
		//鍒ゆ柇琛ㄥ崟鎻愪氦鐨勬暟鎹槸鍚︿负绌�
		if("".equals(userCode)){ //鐢ㄦ埛鍚�
			JsUtil.printAlert(resp, "鐢ㄦ埛鍚嶄笉鑳戒负绌猴紒");
			return;
		}
		
		if("".equals(userPassword)){ //瀵嗙爜
			JsUtil.printAlert(resp, "瀵嗙爜涓嶈兘涓虹┖锛�");
			return;
		}
	
		if("".equals(code)){ //楠岃瘉鐮�
			JsUtil.printAlert(resp, "楠岃瘉鐮佷笉鑳戒负绌猴紒");
			return;
		}
		//session瀵硅薄锛屼細璇濄�瀛樺偍鏁版嵁.浣滅敤鍩燂細娴忚鍣ㄤ笉鍏抽棴鐨勬儏鍐垫暟鎹笉浼氳娓呯┖
		String imgCode=(String)req.getSession().getAttribute("allCode");
		if(!code.equalsIgnoreCase(imgCode)){ //equalsIgnoreCase()蹇界暐澶у皬鍐�
			JsUtil.printAlert(resp, "楠岃瘉鐮佷笉姝ｇ‘锛�");
			return;
		}
		//2.鐢ㄦ埛鍚嶆垨瀵嗙爜鏄惁姝ｇ‘
		UserDao userDao=new UserDao();
		try {
			User user=userDao.login(userCode, userPassword);
			if(user==null){ //鍒ゆ柇褰撳墠鐢ㄦ埛鍚嶆槸鍚﹀瓨鍦�
				JsUtil.printAlert(resp, "璐﹀彿鎴栧瘑鐮佷笉姝ｇ‘锛�");
				return;
			}else{
				//1.璁插綋鍓嶇櫥褰曠殑鐢ㄦ埛淇℃伅瀛樺偍session
				req.getSession().setAttribute("user", user);
				//璇诲彇鎵�睘瑙掕壊
				RoleDao roleDao=new RoleDao();
				Role role=roleDao.getById(user.getUserRole()); //鑾峰彇鍒颁簡瑙掕壊淇℃伅
				req.getSession().setAttribute("role",role); //灏嗚鑹蹭俊鎭瓨鍌ㄥ埌session涓�
				//2.璺宠浆鍒板悗鍙扮鐞嗛〉闈�response.sendRedirct("璺宠浆鍦板潃");
				resp.sendRedirect("admin/main.jsp");
			}
		} catch (Exception e) {
			// TODO 鑷姩鐢熸垚鐨�catch 鍧�
			e.printStackTrace();
		}
	}



}
