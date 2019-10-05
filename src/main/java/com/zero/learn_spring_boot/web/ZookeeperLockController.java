package com.zero.learn_spring_boot.web;

import com.zero.learn_spring_boot.lock.zookeeper.DistributedLock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ZookeeperLockController {

    @RequestMapping("index2")
    public String index()throws Exception{
        DistributedLock lock   = new DistributedLock("localhost:2181","lock");
        lock.lock();
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        //共享资源
        if(lock != null){
            System.out.println("执行方法！" + dateFormat.format(date));
            Thread.sleep(5000);
            lock.unlock();
        }
        return "hello world zookeeper!";
    }
}
