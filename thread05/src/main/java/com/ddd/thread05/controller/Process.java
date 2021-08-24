package com.ddd.thread05.controller;


import com.ddd.thread05.Test02.RandomString;
import com.ddd.thread05.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.System.out;


@Controller
public class Process implements Runnable{
    Memory memory=new Memory(2048);
    View view=new View();
    private boolean cpu_state = true;
    int mark=1;
   static int number=2;
    static int t1 = 0;
    static int t2 = 0;
    static int t3 = 0;
    static int count1,count2,count3;
    //true 空闲   ， false 繁忙
    private static LinkedList<PCB> readyBox = new LinkedList<>();//就绪队列
    private static LinkedList<PCB> blockedBox = new LinkedList<>();//阻塞队列
    private static LinkedList<PCB> cpuBox = new LinkedList<>();//运行队列
    private static LinkedList<PCB> processSum = new LinkedList<>();//所有进程集合
    /*三个队列*/
    private static LinkedList<PCB> firstQueue = new LinkedList<>();//多级反馈队列一
    private static LinkedList<PCB> secondQueue = new LinkedList<>();//多级反馈队列二
    private static LinkedList<PCB> thirdQueue = new LinkedList<>();//多级反馈队列三
    private  static LinkedList<PCB> tempBox=  new LinkedList<>(); //存放未分配内存的进程
    //创建一个进程
    @RequestMapping("/creatThread")
    @ResponseBody
    public View creat(String tnumber,String memoryName) {
        int num = Integer.parseInt(tnumber);
        if (num <= 0)
            return null;
        boolean num_state = true;
        while (num_state) {
            //offer(E e)：在链表尾部插入一个元素
            String name= RandomString.getRandomString();
            int Pid= (int)((Math.random()*9+1)*1000);
            int priority=(int)((Math.random()*9+1));
            int needtime=(int)((Math.random()*9+1));
            int arriveTime=(int)((Math.random()*9+1));
            int size=(int)((Math.random()*1000));
            tempBox.offer(new PCB(Pid, name, priority, needtime, arriveTime, 0, Process_State.READY, 0, size, null));
            num--;
            if (num == 0)
                num_state = false;
        }
        Collections.sort(tempBox,comparatorA);
       for (int i=0;i<tempBox.size();i++){
           Integer startAddress = addThread(memoryName, tempBox.get(i).getMemorySize(),tempBox.get(i).getName());
           if (startAddress!=null){
               tempBox.get(i).setStartAddress(startAddress);
               readyBox.offer( tempBox.get(i));
               tempBox.remove(tempBox.get(i));
               i--;
                }
       }
        return view;
    }
   //根据优先级排序
    static Comparator<PCB> comparatorP = new Comparator<PCB>() {
        @Override
        public int compare(PCB o1, PCB o2) {
            if (o1.getPriority() > o2.getPriority()) {
                return -1;
            } else if (o1.getPriority() < o2.getPriority()) {
                return 1;
            } else {
                if (o1.getPriority() < o2.getPriority()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    };
    //根据响应比排序
    static Comparator<PCB> comparatorR= new Comparator<PCB>() {
        @Override
        public int compare(PCB o1, PCB o2) {
            if (o1.getResponseRatio() > o2.getResponseRatio()) {
                return -1;
            } else if (o1.getResponseRatio() < o2.getResponseRatio()) {
                return 1;
             }
            else {
                if (o1.getResponseRatio() < o2.getResponseRatio()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    };
       //根据到达时间排序
        static Comparator<PCB> comparatorA = new Comparator<PCB>() {
            public int compare(PCB o1, PCB o2) {
                if (o1.getArriveTime() <o2.getArriveTime()) {
                    return -1;
                } else if (o1.getArriveTime() > o2.getArriveTime()) {
                    return 1;
                } else {
                    if (o1.getArriveTime() > o2.getArriveTime()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        };

    //1优先级调度算法
        public boolean tpriority() {
        int count=0;
        Collections.sort(readyBox, comparatorP);
        if (cpu_state) {
            if (!readyBox.isEmpty()) {
                //poll()：查询并移除第一个元素     特有方法
                //offer(E e)：在链表尾部插入一个元素

                for (int i = 0; i <readyBox.size(); i++) {
                    if (!readyBox.isEmpty()){
                        Collections.sort(readyBox, comparatorP);
                    }
                    try {
                        cpuBox.add(readyBox.get(i));
                        readyBox.remove(readyBox.get(i));
                        cpuBox.peek().setProcess_state(Process_State.RUN);
                        try {
                            count=cpuBox.peek().getNeedtime();//进程所需要运行的时间
                            for(int j=0;j<count;j++){
                                Thread.sleep( 1000);
                                cpuBox.peek().setNeedtime(cpuBox.peek().getNeedtime()-1);
                                cpuBox.peek().setAlreadyCpuTime(cpuBox.peek().getAlreadyCpuTime()+1);
                            }
                            cpuBox.peek().setProcess_state(Process_State.FINISH);
                            processSum.add(cpuBox.peek());
                            cpuBox.poll();
                            i--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }catch (NullPointerException e){
                        i--;
                        continue;
                    }

                }


                return true;
            } else {
                System.out.println("就绪队列为空，无法调度");
            }
        }else{
            System.out.println("CPU太忙，无法调度");
            }


        return false;
    }
    //先来先服务
    public boolean fCFS() {
        int count=0;
        Collections.sort(readyBox, comparatorA);
        if (cpu_state) {
            if (!readyBox.isEmpty()) {
                //poll()：查询并移除第一个元素     特有方法
                //offer(E e)：在链表尾部插入一个元素
                for (int i = 0; i <readyBox.size(); i++) {
                    if (!readyBox.isEmpty()){
                        Collections.sort(readyBox, comparatorA);
                    }
                    try {
                        cpuBox.add(readyBox.get(i));
                        readyBox.remove(readyBox.get(i));
                        cpuBox.peek().setProcess_state(Process_State.RUN);
                        try {
                            count=cpuBox.peek().getNeedtime();
                            for(int j=0;j<count;j++){
                                Thread.sleep( 1000);
                                cpuBox.peek().setNeedtime(cpuBox.peek().getNeedtime()-1);
                                cpuBox.peek().setAlreadyCpuTime(cpuBox.peek().getAlreadyCpuTime()+1);
                            }
                            cpuBox.peek().setProcess_state(Process_State.FINISH);
                            processSum.add(cpuBox.peek());
                            cpuBox.poll();
                            i--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }catch (NullPointerException e){
                        i--;
                        continue;
                    }
                }
                return true;
            } else {
                System.out.println("就绪队列为空，无法调度");
            }
        }else{
            System.out.println("CPU太忙，无法调度");
        }

        return false;
    }
    //时间片算法
        public boolean  rR(int timeSliceSize){
            Integer count1= 0;
            Collections.sort(readyBox, comparatorA);
            if (cpu_state) {
                //poll()：查询并移除第一个元素     特有方法
                //offer(E e)：在链表尾部插入一个元素
                if (!readyBox.isEmpty()) {
                    //10 20 5  5 15 0  0 10
                    d:while (true) {
                        for (int i = 0; i < readyBox.size(); i++) {
                            try {
                                cpuBox.add(readyBox.get(i));
                                readyBox.remove(readyBox.get(i));
                                cpuBox.peek().setProcess_state(Process_State.RUN);
                                i--;
                                try {
                                    if (cpuBox.peek().getNeedtime()<=timeSliceSize) {
                                        count1=cpuBox.peek().getNeedtime();
                                        for (int j=0;j<count1;j++){
                                            Thread.sleep(1000);
                                            cpuBox.peek().setNeedtime(cpuBox.peek().getNeedtime()-1);
                                            cpuBox.peek().setAlreadyCpuTime(cpuBox.peek().getAlreadyCpuTime()+1);
                                        }
                                        cpuBox.peek().setProcess_state(Process_State.FINISH);
                                        processSum.add(cpuBox.peek());
                                        cpuBox.poll();
                                    }else{
                                        for (int j=0;j<timeSliceSize;j++){
                                            Thread.sleep(1000);
                                            cpuBox.peek().setNeedtime(cpuBox.peek().getNeedtime()-1);
                                            cpuBox.peek().setAlreadyCpuTime(cpuBox.peek().getAlreadyCpuTime()+1);
                                        }
                                        cpuBox.peek().setProcess_state(Process_State.READY);
                                        readyBox.add(cpuBox.poll());
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }catch (NullPointerException e){
                                i--;
                                continue ;
                            }
                        }
                    }
                } else {
                    System.out.println("就绪队列为空，无法调度");
                }
            }else{
                System.out.println("CPU太忙，无法调度");
            }

            return false;
        }
    //计算响应比方法
    public static void  responseRatio( LinkedList<PCB> linkedList, LinkedList<PCB> processSum){
        float waitTime=0;
        float firstArriveTime=processSum.peek().getArriveTime();
        System.out.println(firstArriveTime);
        for (int i=0;i<processSum.size();i++){
                waitTime+=processSum.get(i).getNeedtime();
        }
        for (int i=0;i<linkedList.size();i++){
            float  count=0;
            count=Math.abs((firstArriveTime+waitTime-linkedList.get(i).getArriveTime()+linkedList.get(i).getNeedtime())/linkedList.get(i).getNeedtime());
            BigDecimal b   =   new   BigDecimal(count);
            float   countt  =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();
            linkedList.get(i).setResponseRatio(countt);
        }
        Collections.sort(linkedList,comparatorR);
    }
        //响应比优先算法
        public boolean hRRN(){
        int count=0;
        int temp=0;//重新给processSum集合里面的needtime赋值
            int n=0;
            PCB poll;
            Collections.sort(readyBox, comparatorA);
            if (cpu_state) {
                if (!readyBox.isEmpty()) {

                    ddd:while (true) {
                    try {
                        if (n!=0) {
                            responseRatio(readyBox,processSum);
                            poll = readyBox.poll();
                            cpuBox.add(poll);
                        }else{
                            poll = readyBox.poll();
                            cpuBox.add(poll);
                        }
                        poll.setProcess_state(Process_State.RUN);
                        try {
                            temp=poll.getNeedtime();
                            count=poll.getNeedtime();
                            for (int j=0;j<count;j++){
                                Thread.sleep(1000);
                                poll.setNeedtime(poll.getNeedtime()-1);
                                poll.setAlreadyCpuTime(poll.getAlreadyCpuTime()+1);
                            }
                            poll.setProcess_state(Process_State.FINISH);
                            cpuBox.remove(poll);
                            poll.setNeedtime(temp);
                            processSum.add(poll);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }catch (NullPointerException e){
                         break ddd;
                    }
                        n++;
                    }
            }else {
                    out.println("就绪队列为空，无法调度");
                }
            }else{
                out.println("CPU太忙，无法调度");
            }

            return false;
        }
        public void MSFQSDDD(LinkedList<PCB>Queue, int timeSliceSize) {
        int count1=0;
            if (cpu_state) {
                if (!Queue.isEmpty()) {
                    mark++;
                    int count = timeSliceSize;

                    ddd:for (int i = 0; i < Queue.size(); i++) {
                        Queue.get(i).setProcess_state(Process_State.RUN);
                        for (int k = i + 1; k < Queue.size(); k++) {
                            if (Queue.get(k).getNeedtime() != 0) {
                                Queue.get(k).setProcess_state(Process_State.READY);
                            }
                        }
                        try {
                            if (Queue.get(i).getNeedtime() <= timeSliceSize) {
                                count1=Queue.get(i).getNeedtime();
                                for (int j=0;j<count1;j++){
                                    Thread.sleep(1000);
                                    Queue.get(i).setNeedtime(Queue.get(i).getNeedtime()-1);
                                    Queue.get(i).setAlreadyCpuTime(Queue.get(i).getAlreadyCpuTime()+1);
                                }
                                Queue.get(i).setProcess_state(Process_State.FINISH);
                                processSum.add(Queue.get(i));
                                Queue.remove(i);
                                i--;
                            } else {
                                for (int j=0;j<timeSliceSize;j++){
                                    Thread.sleep(1000);
                                    Queue.get(i).setNeedtime(Queue.get(i).getNeedtime()-1);
                                    Queue.get(i).setAlreadyCpuTime(Queue.get(i).getAlreadyCpuTime()+1);
                                }
                                Queue.get(i).setProcess_state(Process_State.READY);
                            }
                            if (i == Queue.size() - 1) {
                                if (mark == 2) {
                                    secondQueue.addAll(Queue);
                                    firstQueue.removeAll(Queue);
                                    MSFQSDDD(secondQueue, count2);
                                } else if (mark == 3) {
                                    thirdQueue.addAll(Queue);
                                    secondQueue.removeAll(Queue);
                                    MSFQSDDD(thirdQueue, count3);
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("就绪队列为空，无法调度");
                }
            }else{
                System.out.println("CPU太忙，无法调度");
            }
        }
        public  boolean   MSFQS(int timeSliceSize1,int timeSliceSize2,int timeSliceSize3) {
            count1=timeSliceSize1;
            count2=timeSliceSize2;
            count3=timeSliceSize3;
            if (cpu_state) {
                if (!readyBox.isEmpty()) {
                 Collections.sort(readyBox,comparatorA);
                    firstQueue.addAll(readyBox);
                    readyBox.removeAll(readyBox);
                        MSFQSDDD(firstQueue,timeSliceSize1);
                }else{
                    System.out.println("就绪队列为空，无法调度");
                    }
                } else {
                System.out.println("CPU太忙，无法调度");
                }
                return  false;
    }
    @Override
    public void run() {

        if(Thread.currentThread().getName().equals("tpriority")){
            tpriority();
        }else if(Thread.currentThread().getName().equals("fCFS")){
            fCFS();
        }else if(Thread.currentThread().getName().equals("RR")){
            System.out.println(number);
            rR(number);
        }else if(Thread.currentThread().getName().equals("hRRN")){
            hRRN();
        }else if(Thread.currentThread().getName().equals("MSFQS")){
            MSFQS(t1,t2,t3);
        }else if(Thread.currentThread().getName().equals("blockd")){
            requestIoEvent();
        }


    }
    //创建新的线程
    @RequestMapping("/addThread")
    @ResponseBody
  public  View addThread(Integer priority,Integer needtime,Integer arriveTime,Integer size,String memoryName){
        String name= RandomString.getRandomString();
        int Pid= (int)((Math.random()*9+1)*1000);
        Integer startAddress = addThread(memoryName, size, name);
        if (startAddress!=null){
            readyBox.add(new PCB(Pid,name,priority,needtime,arriveTime,0,Process_State.READY,0,size,startAddress));
        }else{
            tempBox.add(new PCB(Pid,name,priority,needtime,arriveTime,0,Process_State.READY,0,size,null));
        }
        return view;
  }
    @RequestMapping("/dispatch")
    @ResponseBody
  public View dispatch(String name,Integer tnumber,Integer tfirst,Integer ttwo,Integer tthird){
        System.out.println("我来了");
        if (name.equals("RR")) {
            number = tnumber;
        }
        if (name.equals("MSFQS")) {
            t1 = tfirst;
            t2 = ttwo;
            t3 = tthird;
        }
        Process process = new Process();
        if(name.equals("priority")){
            new Thread(process,"tpriority").start();
        }else if(name.equals("fCFS")){
            new Thread(process,"fCFS").start();
        }else if(name.equals("RR")){
            new Thread(process,"RR").start();
        }else if(name.equals("hRRN")){
            new Thread(process,"hRRN").start();
        }else if(name.equals("MSFQS")){
            new Thread(process,"MSFQS").start();
        }else if(name.equals("blockd")){
            new Thread(process,"blockd").start();
        }

        return view;
  }
    @RequestMapping("/checkAllThread")
    @ResponseBody
    public View checkAllThread(){
        view.setFlag(true);
        view.setReadBox(readyBox);
        view.setCpuBox(cpuBox);
        view.setBlockedBox(blockedBox);
        view.setProcessSum(processSum);
        LinkedList<Zone> linkedList=new  LinkedList<>();
        LinkedList<Zone> linkedList2=new  LinkedList<>();
        for (int i=0;i<memory.getZones().size();i++){
            if(memory.getZones().get(i).isFree()){
                Zone zone = memory.getZones().get(i);
                linkedList.add(zone);
            }else{
                Zone zone = memory.getZones().get(i);
                linkedList2.add(zone);
            }
        }
        view.setAllocatedzones(linkedList2);
        view.setSparezones(linkedList);
        view.setNoAllocation(tempBox);
        List<ProcessBar> list=new ArrayList<>();
        ProcessBar processBar=null;
        for (int i=0;i<memory.getZones().size();i++){
            processBar=  new ProcessBar();
            float numbr= memory.getZones().get(i).getSize()/(float)2048*100;
            BigDecimal b   =   new   BigDecimal(numbr);
            float numberr = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            processBar.setFree(memory.getZones().get(i).isFree());
            processBar.setProgressNumber(numberr);
            list.add(processBar);
        }
        view.setProcessBar(list);
        return view;

    }
    @RequestMapping("/checkCpuThread")
    public String checkCpuThread(HttpServletRequest request){
        request.getSession().setAttribute("cpuBox",cpuBox);
        return "index";
    }
    @RequestMapping("/checkBlockThread")
    public String checkBlockThread(HttpServletRequest request){
        request.getSession().setAttribute("blockedBox",blockedBox);
        return "index";
    }
    @RequestMapping("/checkReadyThread")
    public String checkReadyThread(HttpServletRequest request){
        request.getSession().setAttribute("readyBox",readyBox);
        return "index";
    }

    @RequestMapping(value = "/requestIoEvent")
    @ResponseBody
    public View requestIoEvent(){
         for (int i=0;i<cpuBox.size();i++){
             cpuBox.get(i).setProcess_state(Process_State.WAIT);
             blockedBox.add(cpuBox.get(i));
             cpuBox.remove(i);
             i--;
         }
        return view;

    }
    @RequestMapping(value = "/finishIoEvent")
    @ResponseBody
    public View  finishIoEvent(HttpServletRequest request){
        for (int i=0;i<blockedBox.size();i++){
            blockedBox.get(i).setProcess_state(Process_State.READY);
            readyBox.add(blockedBox.get(i));
            blockedBox.remove(i);
            i--;
        }

        return view;
    }
    @RequestMapping("/clean")
    @ResponseBody
    public String clean(HttpServletRequest request){
          processSum.removeAll(processSum);
        for (int i=0;i<thirdQueue.size();i++){
            thirdQueue.remove(i);
        }
        tempBox.removeAll(tempBox);
        return "";
    }

    @RequestMapping("/realaseMemoryByPname")
    @ResponseBody
    public View realaseMemory(String pname){
        System.out.println(pname);
            for ( PCB pcb:readyBox){
                if (pcb.getName().equals(pname)){
                    pcb.setStartAddress(null);
                    tempBox.add(pcb);
                    readyBox.remove(pcb);
                    break;
                }
            }
           memory.collection(pname);
        return view;
    }
   public Integer addThread(String memoryName,int size,String tname){
       if ("fristFit".equals(memoryName)) {
           Integer integer = memory.fristFit(size,tname);
           if (integer != null) {
               return integer;
           }
       }else if("bestFit".equals(memoryName)) {
         Integer integer = memory.bestFit(size,tname);
           if (integer != null) {
               return integer;
           }
       }else if("worstFit".equals(memoryName)){
           Integer integer = memory.worstFit(size,tname);
           if (integer != null) {
               return integer;
           }
       }else if("nextFit".equals(memoryName)){
           Integer integer = memory.nextFit(size,tname);
           if (integer != null) {
               return integer;
           }
       }
        return null;
   }
}
