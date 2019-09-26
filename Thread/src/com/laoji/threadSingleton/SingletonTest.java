package com.laoji.threadSingleton;

import com.laoji.sourceconfilct.SynchronizedDemo;

public class SingletonTest {
    public static void main(String[] args) {
        Runnable r=()->{
          Boss.getBoss();
        };
        for (int i=0;i<100;i++){
            new Thread(r).start();
        }
    }
}
class Boss{
    private Boss(){
        System.out.println("一个Boss对象被实例化了");
    }
    private static Boss Instance=null;
    /*public static Boss getBoss(){
        synchronized (""){
            if(Instance==null){
                Instance=new Boss();
            }
        }
        return Instance;
    }*/
    public synchronized static  Boss getBoss(){
        if(Instance==null){
            Instance=new Boss();
        }

        return Instance;
    }
}