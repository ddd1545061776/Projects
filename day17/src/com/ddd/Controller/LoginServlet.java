package com.ddd.Controller;

import com.ddd.entiy.User;
import com.ddd.service.UserService;
import com.ddd.service.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //获取验证码
        String verifycode = request.getParameter("verifycode");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
       request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (!(checkcode_server.equalsIgnoreCase(verifycode))) {
            //验证码不正确
            request.setAttribute("login_msg", "验证码错误!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            UserService service=new UserServiceImpl();
            User login_User = service.login(user);
            if(login_User!=null){
                //登录成功
                request.getSession().setAttribute("login_User",login_User);
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }else{
                request.setAttribute("login_msg", "用户名或者密码错误!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
