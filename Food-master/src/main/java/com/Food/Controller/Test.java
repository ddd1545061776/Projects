package com.Food.Controller;

import com.alibaba.fastjson.JSONObject;

public class Test {
    public static void main(String[] args) {
        String verifyCode="132132";
        JSONObject json=new JSONObject();
        json = new JSONObject();
        json.put("verifyCode", verifyCode);
        json.put("createTime", System.currentTimeMillis());
        System.out.println(json.getString("verifyCode"));
        System.out.println(json.getIntValue("verifyCode"));
        // 将认证码存入SESSION

    }
}
