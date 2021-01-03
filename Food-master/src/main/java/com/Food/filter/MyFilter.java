package com.Food.filter;

import com.Food.Entity.Shop;
import com.Food.Entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user =(User)request.getSession().getAttribute("USER_SESSION");
        Shop shop = (Shop)request.getSession().getAttribute("SHOP_SESSION");
        if (("null".equals(user)||null==user)&&("null".equals(shop)||null==shop)){
    response.sendRedirect("/Food/login.html");
    return false;
         }else{
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
