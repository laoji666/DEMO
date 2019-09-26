package com.laoji.procuctorAndConsumer;

import java.util.LinkedList;
import java.util.List;

public class ProductPool {
    //存储有所有产品的集合，生产者生产产品，往这个集合添加元素，消费者消费产品，从这个集合中取出元素。
    private List<Product> productList;
    private int maxSize=0;
    public ProductPool(int maxSize){
        //对产品池进行实例化
        this.productList=new LinkedList<Product>();
        this.maxSize=maxSize;
    }

    /**
     * 生产者将生产好的商品放入商品池
     * @param product
     * 非静态同步方法持有的锁对象是this
     */
    public synchronized void push(Product product){
        //判断是否还需要再生产产品
        if(this.productList.size()==maxSize){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //将产品添加到集合中
        this.productList.add(product);
        //通知其他人，有产品可以消费了
        this.notifyAll();
    }

    /**
     * 消费者从商品池中选择一件商品消费
     * @return
     */
    public synchronized Product pop(){
        //判断是否还有商品可消费
        if(this.productList.size()==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //从商品池中取出一件商品
        Product product=this.productList.remove(0);
        this.notifyAll();

        return product;
    }
}
