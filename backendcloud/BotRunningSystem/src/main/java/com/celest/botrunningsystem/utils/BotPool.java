package com.celest.botrunningsystem.utils;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j
@Component
public class BotPool extends Thread {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Queue<Bot> botQueue = new LinkedList<>();

    @PostConstruct
    public void init(){
        this.start();
    }

    public void addBot(int userId ,String code,String input){
        lock.lock();
        try{
           this.botQueue.offer( new Bot(userId,code,input));
           condition.signalAll();
        }finally{
            lock.unlock();
        }

    }

    @Override
    public void run() {
        while(true){
            lock.lock();
            if(botQueue.isEmpty()){
                try{
                    condition.await();
                }catch (InterruptedException e){
                    log.error(e.getMessage());
                    lock.unlock();
                    break;
                }
            }else {
                Bot bot = botQueue.poll();
                lock.unlock();
                try {
                    consume(bot);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private void consume(Bot bot) throws InterruptedException {
        Consumer con = new Consumer();
        con.setTimeOut(bot,2000);
    }
}
