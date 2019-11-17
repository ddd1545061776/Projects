package com.ddd.Controller;

import com.ddd.entiy.User;
import com.ddd.service.UserService;
import com.ddd.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserByIdServlet")
public class FindUserByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           request.setCharacterEncoding("utf-8");
        String id = request.getParameter("id");
        int i = Integer.parseInt(id);
        UserService userService=new UserServiceImpl();
       User user= userService.findByIdUser(i);
         request.setAttribute("user",user);
         request.getRequestDispatcher("/update1.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
