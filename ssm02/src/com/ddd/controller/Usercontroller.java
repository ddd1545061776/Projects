package com.ddd.controller;

import java.util.List;

import javax.annotation.Resource;   

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ddd.pojo.User;

import com.ddd.service.UserService;
import com.ddd.service.UserServiceimpl;
@Controller
public class Usercontroller {
	@Autowired
	UserService userService;
	    @RequestMapping("/www.do")
        public String selectlogin(String username,String password) throws Exception{
	               User user=new User();
	               user.setPassword(password);
	               user.setUsername(username);
  User user1	=userService.findUserBynamepwd(user);
	System.out.println(user1);
			      if(user1!=null){
			    	  return "welcome";
			      }else{
				return "fial" ;
			      }
        }
	   
	    
}
