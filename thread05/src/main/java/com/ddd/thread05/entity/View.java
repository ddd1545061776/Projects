package com.ddd.thread05.entity;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class View implements Serializable {
   private LinkedList<PCB> readBox;
    private LinkedList<PCB> blockedBox;
    private LinkedList<PCB> cpuBox;
    private LinkedList<PCB> processSum;
    private  LinkedList<PCB> firstQueue;
    private  LinkedList<PCB> secondQueue ;
    private  LinkedList<PCB> thirdQueue ;
    private LinkedList<Zone> allocatedzones;//内存分配集合
    private LinkedList<Zone> sparezones;
    private LinkedList<PCB> noAllocation;
    private List<ProcessBar> processBar;

    public List<ProcessBar> getProcessBar() {
        return processBar;
    }

    public void setProcessBar(List<ProcessBar> processBar) {
        this.processBar = processBar;
    }

    public LinkedList<PCB> getNoAllocation() {
        return noAllocation;
    }

    public void setNoAllocation(LinkedList<PCB> noAllocation) {
        this.noAllocation = noAllocation;
    }

    public LinkedList<Zone> getSparezones() {
        return sparezones;
    }

    public void setSparezones(LinkedList<Zone> sparezones) {
        this.sparezones = sparezones;
    }

    public LinkedList<Zone> getAllocatedzones() {
        return allocatedzones;
    }

    public void setAllocatedzones(LinkedList<Zone> allocatedzones) {
        this.allocatedzones = allocatedzones;
    }

    private boolean flag;
    public LinkedList<PCB> getFirstQueue() {
        return firstQueue;
    }

    public void setFirstQueue(LinkedList<PCB> firstQueue) {
        this.firstQueue = firstQueue;
    }

    public LinkedList<PCB> getSecondQueue() {
        return secondQueue;
    }

    public void setSecondQueue(LinkedList<PCB> secondQueue) {
        this.secondQueue = secondQueue;
    }

    public LinkedList<PCB> getThirdQueue() {
        return thirdQueue;
    }

    public void setThirdQueue(LinkedList<PCB> thirdQueue) {
        this.thirdQueue = thirdQueue;
    }




    public LinkedList<PCB> getBlockedBox() {
        return blockedBox;
    }

    public void setBlockedBox(LinkedList<PCB> blockedBox) {
        this.blockedBox = blockedBox;
    }

    public LinkedList<PCB> getCpuBox() {
        return cpuBox;
    }

    public void setCpuBox(LinkedList<PCB> cpuBox) {
        this.cpuBox = cpuBox;
    }

    public LinkedList<PCB> getProcessSum() {
        return processSum;
    }

    public void setProcessSum(LinkedList<PCB> processSum) {
        this.processSum = processSum;
    }

    public LinkedList<PCB> getReadBox() {
        return readBox;
    }

    public void setReadBox(LinkedList<PCB> readBox) {
        this.readBox = readBox;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
