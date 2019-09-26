package com.laoji.procuctorAndConsumer;

public class Program {
    /*
    * 生产者
    *
    * 作用是生产产品
    * 生产逻辑:通过一个生产标记，判断是否需要生产产品。
    * 如果需要生产:生产产品，并通知消费者消费。
    * 如果不需要生产: 等待。
    * */

    /*
    * 消费者
    *
    * 作用是消费产品
    * 消费逻辑：判断是否有足够的产品可以消费。
    * 如果可以消费，获取产品，进行消费、
    * 如果不可以消费: 等待。
    * */
    public static void main(String[] args) {
        //实例化一个产品池
        ProductPool pool =new ProductPool(15);
        //实例化一个生产者
        new Productor(pool).start();
        //实例化一个消费者
        new Consumer(pool).start();
    }
}
