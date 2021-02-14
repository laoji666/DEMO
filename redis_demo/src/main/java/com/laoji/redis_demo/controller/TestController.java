package com.laoji.redis_demo.controller;


import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    public static final String LOCK = "redis_lock";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Redisson redisson;

    @Resource
    Environment environment;

    @GetMapping("buy2")
    public String buy2(){
        // 采用redisson方式来实现分布式锁
        RLock redisLock = redisson.getLock(LOCK);
        redisLock.lock();
        try {
            String good = stringRedisTemplate.opsForValue().get("good");
            Integer goodNum = good == null ? 0 : Integer.parseInt(good);
            if (goodNum > 0) {
                // 数量-1
                Integer newNum = goodNum - 1;
                System.out.println("购买商品成功，剩余商品数量为：" + newNum + " 服务器端口为：" + environment.getProperty("local.server.port"));
                stringRedisTemplate.opsForValue().set("good", newNum + "");
                return "购买商品成功，剩余商品数量为：" + newNum + " 服务器端口为：" + environment.getProperty("local.server.port");
            } else {
                System.out.println("库存为0，购买失败");
            }
            return "库存为0，购买失败";
        } finally {
            if(redisLock.isHeldByCurrentThread() && redisLock.isLocked()){
                redisLock.unlock();
            }
        }
    }

    @GetMapping("buy")
    public String buy() {
        /*
        * 这种方式依然存在问题
        * 比如锁的过期时间比业务时间短 有可能因为锁已经被释放了，然而业务还没走完
        * */
        String value = UUID.randomUUID().toString();
        try {
            // 创建锁
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(LOCK, value, 10L, TimeUnit.SECONDS);
            if (!result) {
                return "锁存在";
            }
            String good = stringRedisTemplate.opsForValue().get("good");
            Integer goodNum = good == null ? 0 : Integer.parseInt(good);
            if (goodNum > 0) {
                // 数量-1
                Integer newNum = goodNum - 1;
                System.out.println("购买商品成功，剩余商品数量为：" + newNum + " 服务器端口为：" + environment.getProperty("local.server.port"));
                stringRedisTemplate.opsForValue().set("good", newNum + "");
                return "购买商品成功，剩余商品数量为：" + newNum + " 服务器端口为：" + environment.getProperty("local.server.port");
            } else {
                System.out.println("库存为0，购买失败");
            }
            return "库存为0，购买失败";
        } finally {
            try {
                // 下面这种方式可以保证原子性，为redis官方推荐方法
                RedisScript<Long> redisScript = RedisScript.of("if redis.call('get',KEYS[1]) == ARGV[1] " +
                        "then " +
                        "    return redis.call('del',KEYS[1]) " +
                        "else " +
                        "    return 0 " +
                        "end", Long.class);
                Long o = stringRedisTemplate.execute(redisScript, Collections.singletonList(LOCK), value);
                /*if (o == 0L) {
                    System.out.println("删除锁失败...");
                } else {
                    System.out.println("删除锁成功");
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
