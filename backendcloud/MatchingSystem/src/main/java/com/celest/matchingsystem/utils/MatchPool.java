package com.celest.matchingsystem.utils;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
@Component
public class MatchPool extends Thread{
    private List<Player> users = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private RestTemplate restTemplate ;
    private final static Logger log = LoggerFactory.getLogger(MatchPool.class);

    private static final String startGameUrl = "http://127.0.0.1:3000/match/startGame";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @PostConstruct
    public void init(){
        this.start();
    }
    private void waitTimeAutoIncrement(){
        lock.lock();
        try{
            for (Player user : users)  user.autoIncrement();
        }finally{
            lock.unlock();
        }
    }
    public void  addUser(Integer userId,Integer rating){
        lock.lock();
        try{
            Player player = new Player(userId, rating, 0);
            this.users.add(player);
        }finally{
            lock.unlock();
        }
    }

    public void removeUser(Integer userId){
        lock.lock();
        try{
            ArrayList<Player> users = new ArrayList<>();
            for (Player user : this.users) {
                if(!user.getUserId().equals(userId)) users.add(user);
            }
            this.users = users;
        }finally{
            lock.unlock();
        }
    }

    private void sendMatchResult(Integer userA,Integer userB){
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("userA",userA.toString());
        data.add("userB",userB.toString());
        Result<?> success = restTemplate.postForObject(startGameUrl, data, Result.class);
        if(success!=null){
            System.out.println("code:"+ success.getCode());
        }

    }


    private boolean checkMatch(Player userA,Player userB){
        int diffScore = Math.abs(userA.getRating() - userB.getRating());
        int waitTime = Math.min(userA.getWaitTime(),userB.getWaitTime());
        log.info("waitTime: {}" , waitTime);
        return diffScore <= waitTime*10;
    }
    private void tryMatch(){
        boolean[] matched = new boolean[users.size()];

        for(int i=0;i<users.size();i++){
            if(matched[i])continue;
            for(int j=i+1;j<users.size();j++){
                if(matched[j]||!checkMatch(users.get(i),users.get(j))) continue;
                matched[i]=matched[j]=true;
                sendMatchResult(users.get(i).getUserId(),users.get(j).getUserId());
                break;
            }
        }
        List<Player> players = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            if(matched[i]) continue;
            players.add(users.get(i));
        }
        this.users = players;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                lock.lock();
                try{
                    waitTimeAutoIncrement();
                    tryMatch();
                }finally{
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                log.error("匹配池InterruptedException:{}",e.getMessage());
                break;
            }
        }
    }
}
