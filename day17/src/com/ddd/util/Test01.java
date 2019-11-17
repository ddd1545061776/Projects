package com.ddd.util;

import java.util.ArrayList;
import java.util.List;

public class Test01 {
    public static void main(String[] args) {
        String[] sBuffer = new String[1];
        System.out.println(sBuffer);
        List<String> list=new ArrayList<>();
        list.add("大坏蛋");
        System.out.println(list.toArray());
      String[] sb_string = list.toArray(new String[2]);
    }
}
