package com.ddd.thread05.entity;

import java.io.Serializable;

public class Zone implements Serializable {
    //进程名字
    private  String name;
    // 分区大小
    private Integer size;
    //分区始址
    private Integer head;
    //空闲状态
    private boolean isFree;

    public Zone(Integer head, Integer size) {
        this.head = head;
        this.size = size;
        this.isFree = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
