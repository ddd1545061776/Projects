package com.ddd.controller;

import java.io.IOException;
import java.util.Map;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddd.entity.User;
import com.ddd.service.BaseDictService;
import com.ddd.service.UserService;

@Controller
public class LoginController {
	@Autowired
	  private UserService userService;
	@RequestMapping("/login")
	public String  login(HttpServletRequest request,HttpServletResponse response,User user) throws IOException {
	//获取验证码
    String verifycode = request.getParameter("verifycode");
    String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
   request.getSession().removeAttribute("CHECKCODE_SERVER");
    if (!(checkcode_server.equalsIgnoreCase(verifycode))||verifycode=="null") {
        //验证码不正确
        request.setAttribute("login_msg", "验证码错误!");
      return "login";
    } else {
        User login_User = userService.login(user);
        if(login_User!=null){
            //登录成功
            request.getSession().setAttribute("login_User",login_User);
            response.sendRedirect(request.getContextPath()+"/list");
        }else{
            request.setAttribute("login_msg", "用户名或者密码错误!");
            return "login";
        }
    }
	  return "login";
}
	
}
