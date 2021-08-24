package com.ddd.thread05.Test02;


public class TestInteger {
    Integer  a=127;
    Integer b=127;
    Integer c=128;
    Integer d=128;
    int g=127;
    Integer h=127;
    int e=-128;
    Integer f=-128;

    public void ddd(){
        System.out.println(a==b);
        System.out.println(c==d);
        System.out.println(g==h);
        System.out.println(e==f);
    }


    public void dd(){
        int needtime=(int)((Math.random()*1000));
        System.out.println(needtime);
    }
}
