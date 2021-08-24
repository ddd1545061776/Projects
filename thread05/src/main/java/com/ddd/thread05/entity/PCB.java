package com.ddd.thread05.entity;

import java.io.Serializable;

public class PCB implements Serializable {
    private Integer pid;
    private    String name;
    private  Integer priority;
    private  Integer   needtime;
    private Integer arriveTime;
    private Integer alreadyCpuTime;
    private Process_State process_state;
    private   float responseRatio;
    private Integer memorySize;
    private Integer startAddress;

    public Integer getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public Integer getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Integer startAddress) {
        this.startAddress = startAddress;
    }

    public PCB(Integer pid, String name, Integer priority, Integer needtime, Integer arriveTime, Integer alreadyCpuTime, Process_State process_state, float responseRatio, Integer memorySize, Integer startAddress) {
        this.pid = pid;
        this.name = name;
        this.priority = priority;
        this.needtime = needtime;
        this.arriveTime = arriveTime;
        this.alreadyCpuTime = alreadyCpuTime;
        this.process_state = process_state;
        this.responseRatio = responseRatio;
        this.memorySize = memorySize;
        this.startAddress = startAddress;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getNeedtime() {
        return needtime;
    }

    public void setNeedtime(Integer needtime) {
        this.needtime = needtime;
    }



    public Integer getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Integer arriveTime) {
        this.arriveTime = arriveTime;
    }
    public Integer getAlreadyCpuTime() {
        return alreadyCpuTime;
    }

    public void setAlreadyCpuTime(Integer alreadyCpuTime) {
        this.alreadyCpuTime = alreadyCpuTime;
    }

    public Process_State getProcess_state() {
        return process_state;
    }

    public void setProcess_state(Process_State process_state) {
        this.process_state = process_state;
    }

    public float getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(float responseRatio) {
        this.responseRatio = responseRatio;
    }
}
