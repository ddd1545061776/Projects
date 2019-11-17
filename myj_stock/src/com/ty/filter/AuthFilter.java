package com.ty.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ty.entity.User;

@WebFilter(urlPatterns="/admin/*") //设置拦截的请求地址
public class AuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	/**
	 * 拦截请求地址方法
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		 //1.验证用户是否登录
		HttpServletRequest request=(HttpServletRequest)req; //将请求对象父类转换为子类实例对象
		HttpServletResponse response=(HttpServletResponse)resp; //将响应对象父类转换为子类实例对象
		HttpSession session=request.getSession(); //获取session对象
		//获取session存储的用户信息
		User user=(User)session.getAttribute("user");
		if(user==null){ //判断用户是否登录
			//跳转到登录页面,..返回到上一级目录
			response.sendRedirect("../login.jsp"); 
		}else{
			chain.doFilter(req, resp); //调用真实的请求路径
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

}
