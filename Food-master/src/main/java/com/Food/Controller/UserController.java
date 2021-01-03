package com.Food.Controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.Food.email.MailUtils;
import com.Food.email.TestMail;
import com.Food.vo.Vo;
import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Food.Entity.User;
import com.Food.Service.UserService;

@Controller   //controller对象
public class UserController {

	@Resource
	private UserService userService;

	/*
	 * 用户登录
	 */
	@RequestMapping(value="/UserLogin")      //处理请求的处理器
	@ResponseBody
	public Vo UserLogin(HttpServletRequest request){
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		//查询用户
		Vo vo=new Vo();
		User model = userService.UserLogin(email,password);
		if (model==null){
			vo.setMessage("邮箱或者密码错误");
			return  vo;
		}
		if (model.getStatus()==0){
			vo.setMessage("邮箱未激活");
			return vo;
		}else{
			request.getSession().setAttribute("USER_SESSION",model);
			vo.setUser(model);
			return vo;
		}
	}
	/**
	 * 用户注册
	 */
	@RequestMapping(value="/UserRegister")
	@ResponseBody
	public String UserRegister(HttpServletRequest request){
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		//查询用户
		User model = new User();
		if(email==""||password==""){
			return "error";
		}else{
			model.setEmail(email);
			model.setPassword(password);			
			model.setId(UUID.randomUUID().toString());
			model.setMoney(100);
			userService.UserRegister(model);
		request.getSession().setAttribute("USERID",model.getId());
			request.getSession().setAttribute("USEREMAIL",model.getEmail());
			return "true";
		}
	}

	@RequestMapping(value="/UserEdit")
	@ResponseBody
	public String UserEdit(HttpServletRequest request){
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String userId = request.getParameter("userId");
		String money = request.getParameter("money");
		//查询用户
		User model = userService.GetUserById(userId);
		if(model!=null)
		{
			model.setEmail(email);
			model.setPassword(password);
			model.setMoney(Double.valueOf(money));
			userService.UserEdit(model);
			request.getSession().removeAttribute("USER_SESSION");
		}

		return "success";
	}

	@RequestMapping(value="/GetUserById")
	@ResponseBody
	public User GetUserById(HttpServletRequest request){
		String userId = request.getParameter("userId");
		User model = userService.GetUserById(userId);
		return model;
	}

	@RequestMapping(value="/GetAllUser")
	@ResponseBody
	public List<User> GetAllUser(HttpServletRequest request){
		return userService.GetAllUser();
	}

	@RequestMapping(value="/DeleteUser")
	@ResponseBody
	public String DeleteUser(HttpServletRequest request){
		String userId = request.getParameter("userId");
		 userService.DeleteUser(userId);
		 return "success";
	}
	@RequestMapping(value="/GetEmail")
	@ResponseBody
	public String getPhone(HttpServletRequest request){
		User user =(User) request.getSession().getAttribute("USER_SESSION");
	   if (user!=null) {
		   String phone = user.getEmail();
		   return phone;
	   }
	   return "";
	}
	@RequestMapping(value="/login_out")
	public String login_out(HttpServletRequest request){
		request.getSession().removeAttribute("USER_SESSION");
		request.getSession().invalidate();
		return  "/login";
	}
	@RequestMapping(value="/active")
	public String active(HttpServletRequest request,String code){
        userService.activeUser(code);
		return  "/login";
	}
	/**
	 * 发送短信验证码
	 * @param
	 */
	@RequestMapping("/sendSms")
	@ResponseBody
	public Object sendSms(HttpServletRequest request, String phone) {
		try {
			JSONObject json = null;
			//生成6位验证码
			String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
			//发送短信
			ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com","107677", "0fa1fd8a-1011-4da2-b003-86f9bb957352");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("number", phone);
			params.put("templateId", "3013");
			String[] templateParams = new String[2];
			templateParams[0] = verifyCode;
			templateParams[1] = "5分钟";
			params.put("templateParams", templateParams);
			String result = client.send(params);
			json = JSONObject.parseObject(result);
			if(json.getIntValue("code") != 0)//发送短信失败
				return "fail";
			//将验证码存到session中,同时存入创建时间
			//以json存放，这里使用的是阿里的fastjson
			HttpSession session = request.getSession();
			json = new JSONObject();
			json.put("verifyCode", verifyCode);
			json.put("createTime", System.currentTimeMillis());
			// 将认证码存入SESSION
            session.setAttribute("verifyCode", json);
			session.setMaxInactiveInterval(300);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/sendEmail")
	@ResponseBody
	public String sendEmail(HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("USERID");
		String useremail = (String)request.getSession().getAttribute("USEREMAIL");
		String content="<a href='http://47.115.13.19:8080/Food/active?code="+userid+"'>点击激活【小冬订餐系统】</a>";
		MailUtils.sendMail(useremail,content,"账号激活邮件");
		return  "true";
	}

}
