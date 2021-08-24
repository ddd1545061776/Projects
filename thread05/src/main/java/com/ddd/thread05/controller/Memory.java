package com.ddd.thread05.controller;

import com.ddd.thread05.entity.Zone;

import java.util.LinkedList;


public class Memory {
        //内存大小
        private Integer size;
        //设置内存值
        private static final Integer MAX_SIZE = 2048;

        public static Integer getMaxSize() {
            return MAX_SIZE;
        }
        //内存分区
        private LinkedList<Zone> zones;
        //上次分配的空闲区位置
        private Integer pointer;

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public LinkedList<Zone> getZones() {
            return zones;
        }
        public void setZones(LinkedList<Zone> zones) {
            this.zones = zones;
        }
        public Integer getPointer() {
            return pointer;
        }
        public void setPointer(Integer pointer) {
            this.pointer = pointer;
        }
    //默认内存大小2048K
    public Memory(){
        this.size = MAX_SIZE;
        this.pointer = 0;
        this.zones = new LinkedList<>();
        zones.add(new Zone(0, size));
    }
    public Memory(int size) {
        this.size = size;
        this.pointer = 0;
        this.zones = new LinkedList<>();
        zones.add(new Zone(0, size));
    }





    // FF

    public Integer fristFit(int size,String name){
        //遍历分区链表
        for (pointer = 0; pointer < zones.size(); pointer++){
            Zone tmp = zones.get(pointer);
            //找到可用分区（空闲且大小足够）
            if (tmp.isFree() && (tmp.getSize() > size)){
                tmp.setName(name);
                doAllocation(size, pointer, tmp);
                return tmp.getHead();
            }
        }
        //遍历结束后未找到可用分区, 则内存分配失败
        return null;
    }

    //NF
    public Integer nextFit(int size,String name){
        Zone tmp = zones.get(pointer);
        if (tmp.isFree() && (tmp.getSize() > size)){
            doAllocation(size, pointer, tmp);
            return tmp.getHead();
        }
        int len = zones.size();
        int i = (pointer + 1) % len;
        for (;i != pointer; i = (i+1) % len){
            tmp = zones.get(i);
            if (tmp.isFree() && (tmp.getSize() > size)){
                tmp.setName(name);
                doAllocation(size, i, tmp);
                return tmp.getHead();
            }
        }
        //全遍历后如果未分配则失败
        return  null;
    }

    //BF
    public Integer bestFit(int size,String name){
        int flag = -1;
        int min = this.size;
        for (pointer = 0; pointer < zones.size(); pointer++){
            Zone tmp = zones.get(pointer);
            if (tmp.isFree() && (tmp.getSize() > size)){
                if (min > tmp.getSize() - size){
                    min = tmp.getSize() - size;
                    flag = pointer;
                }
            }
        }
        if (flag == -1){
            return null;
        }else {
            zones.get(flag).setName(name);
            doAllocation(size, flag, zones.get(flag));
        }
        return zones.get(flag).getHead();
    }

    //WF
    public Integer worstFit(int size,String name){
        int flag = -1;
        int max = 0;
        for (pointer = 0; pointer < zones.size(); pointer++){
            Zone tmp = zones.get(pointer);
            if (tmp.isFree() && (tmp.getSize() > size)){
                if (max < tmp.getSize() - size){
                    max = tmp.getSize() - size;
                    flag = pointer;
                }
            }
        }
        if (flag == -1){
            return null;
        }else {
            zones.get(flag).setName(name);
            doAllocation(size, flag, zones.get(flag));
        }
        return zones.get(flag).getHead();
    }

    //开始分配
    private void doAllocation(int size, int location, Zone tmp) {
            Zone split = new Zone(tmp.getHead() + size, tmp.getSize() - size);
            zones.add(location + 1, split);
            tmp.setSize(size);
            tmp.setFree(false);
    }


    //内存回收
    public void collection(String name){
        for (int i=0;i<zones.size();i++){
            if (zones.get(i).getName().equals(name)){
                //如果回收的分区后一个是空闲就和后一个合并
                if (i<zones.size()-1&&zones.get(i+1).isFree()){
                    Zone next = zones.get(i + 1);
                    zones.get(i).setSize( zones.get(i).getSize()+next.getSize());
                    zones.get(i).setFree(true);
                    zones.remove(next);
                    i--;
                    //回收的分区要是前一个是空闲就和前分区合并
                }else if (i>0&&zones.get(i-1).isFree()){
                    Zone previous = zones.get(i - 1);
                    previous.setSize(previous.getSize()+zones.get(i).getSize());
                    zones.remove(i);
                    i--;
                    return;
                }else{
                    zones.get(i).setFree(true);
                }
            }

        }
    }



}
