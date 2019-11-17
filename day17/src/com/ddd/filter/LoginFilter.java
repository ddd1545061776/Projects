package com.ddd.filter;

import com.ddd.entiy.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(uri);
        if(uri.contains("/loginjsp")||uri.contains("/loginServlet")||uri.contains("/css/")||uri.contains("/js/")||uri.contains("/fonts/")||uri.contains("/checkCodeServlet")){
            chain.doFilter(req, resp);
        }else{
            User user = (User) request.getSession().getAttribute("login_User");
            if(user!=null||"".equals(user)){
                chain.doFilter(req, resp);
            }else{
                request.setAttribute("login_msg","您尚未登录，请先登录");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
