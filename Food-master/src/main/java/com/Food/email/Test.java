package com.Food.email;

public class Test implements  Runnable{
    @Override
    public void run() {
            MailUtils.sendMail("1294276034@qq.com", "你好，这是一封测试邮件，无需回复", "激活邮件");
    }
    public static void main(String[] args) {

            new Thread(new Test()).start();

    }
}
