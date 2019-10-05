package com.zero.learn_spring_boot.web;

import com.zero.learn_spring_boot.lock.redisson.AquiredLockWorker;
import com.zero.learn_spring_boot.lock.redisson.DistributedLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class RedissonLockController {

    @Autowired
    private DistributedLocker distributedLocker;

    @RequestMapping("index1")
    public String index()throws Exception{

        distributedLocker.lock("test",new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() {
                try {
                    Date date = new Date();
                    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
                    System.out.println("执行方法！" + dateFormat.format(date));
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

        });
        return "hello world!";
    }
}

