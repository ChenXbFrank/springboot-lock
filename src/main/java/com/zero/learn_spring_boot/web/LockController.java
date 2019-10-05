package com.zero.learn_spring_boot.web;


import com.zero.learn_spring_boot.lock.redis.DistributedLockHandler;
import com.zero.learn_spring_boot.lock.redis.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class LockController {
    @Autowired
    private DistributedLockHandler distributedLockHandler;

    @RequestMapping("index")
    public String index(){
        Lock lock=new Lock("lynn","min");
        if(distributedLockHandler.tryLock(lock)){
            try {
                Date date = new Date();
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
                //为了演示锁的效果，这里睡眠5000毫秒
                System.out.println("执行方法" + dateFormat.format(date));
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            distributedLockHandler.releaseLock(lock);
        }
        return "hello world!";
    }

}
