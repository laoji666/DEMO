package com.laoji.thread;

public class ThreadCreate {
    public static void main(String[] args) {
        MyThread mt=new MyThread();

        mt.start();

        Runnable r=() -> {
            for (int i=0;i<10;i++){
                System.out.println("!!!!"+i);
            }
        };
        Thread t2=new Thread(r);
        t2.start();
        System.out.println("？？？？？？？");
    }
}
//可读性强
class MyThread extends Thread{
    @Override
    public void run() {
        for (int i=0;i<10;i++){
            System.out.println("？？"+i);
        }
    }
}
