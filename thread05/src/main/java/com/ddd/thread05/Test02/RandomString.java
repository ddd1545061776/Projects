package com.ddd.thread05.Test02;

import java.util.Random;

public class RandomString  {


    public static String getRandomString( ){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++){
            int number=random.nextInt(26);
            sb.append(str.charAt(number));
        }
       return sb.toString();
    }

}
