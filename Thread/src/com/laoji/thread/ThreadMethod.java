package com.laoji.thread;

public class ThreadMethod {
    public static void main(String[] args) {
        //1.实例化一个线程对象
//        threadSleep();
//        threadPriority();
        threadYield();
    }
    /*线程礼让*/
    private static void threadYield(){
        //线程礼让，指的是让当前的运行状态的线程释放自己的CPU资源，由运行状态，回到就绪状态
        Runnable r=new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(Thread.currentThread().getName()+" : "+i);
                    if(i==3){
                        Thread.yield();
                    }
                }
            }
        };
        Thread t1=new Thread(r,"thread-1");
        Thread t2=new Thread(r,"thread-2");

        t1.start();
        t2.start();
    }
    /*线程优先级*/
    private static void threadPriority(){
        //设置线程优先级，只是修改这个线程可以去抢到CPU时间片的概率
        //并不是优先级高的线程一定能抢到CPU时间片
        //优先级的设置，是一个整数[0,10] 的整数 默认是5
        Runnable r=()->{
            for (int i=0;i<100;i++){
                System.out.println(Thread.currentThread().getName()+" : "+i);
            }
        };
        Thread t1=new Thread(r,"Thread-1");
        Thread t2=new Thread(r,"Thread-2");
        //设置优先级
        //设置优先级必须要放到这个线程开始执行(start)之前
        t1.setPriority(1);
        t2.setPriority(10);
        t1.start();
        t2.start();
    }
    /*线程休眠*/
    private static void threadSleep(){
        MyThread2 thread2=new MyThread2("666");
        thread2.start();
    }
}
class MyThread2 extends Thread{
    public MyThread2(String name){
//        this.setName(name);
        super(name);
    }

    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println(i);
            try{
                //参数为毫秒为单位
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}