package com.Food.Controller;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verifyCode="132132";
        JSONObject json=new JSONObject();
        json = new JSONObject();
        json.put("verifyCode", verifyCode);
        json.put("createTime", System.currentTimeMillis());
        System.out.println(json.getString("verifyCode"));
        System.out.println(json.getIntValue("verifyCode"));
        // 将认证码存入SESSION
        request.getSession().setAttribute("verifyCode",json);
        Map<String,String> verifyCode1 =(Map<String,String>) request.getSession().getAttribute("verifyCode");
//        JSONObject json1=new JSONObject();
        System.out.println(verifyCode1.get("verifyCode"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
