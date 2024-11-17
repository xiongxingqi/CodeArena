package com.celest.backend.utils.game;

import cn.hutool.json.JSONObject;
import com.celest.backend.comsumer.WebSocketServer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class Game extends Thread{
    //地图的高
    private final Integer rows;
    //地图的宽
    private final Integer cols;

    //地图的障碍物的数量
    private final Integer innerWallsCount;
    //四个方向的偏移量
    private final int[] dx={0,-1,0,1},dy={1,0,-1,0};
    //地图
    @Getter
    private final int[][] map;
    //两个玩家
    @Getter
    private final Player playerA;
    @Getter
    private final Player playerB;

    private Integer nextstepA;
    private Integer nextstepB;
    //
    private final ReentrantLock lock = new ReentrantLock();
    private String status="playing"; // playing -> finished
    private String loser = ""; // all A B


    @Override
    public void run() {
        for(int i=0; i<1000; i++){
            if(nextStep()){//成功进入下一回合
                //判断玩家下一步是否合法
                judge();
                if(this.status.equals("playing")){
                    sendMove();
                }else {
                    sendResult();
                    break;
                }
            }else {
                //玩家未在指定时间输入操作,判负
                this.status = "finished";
                lock.lock();
                try{
                    if(nextstepB == null && nextstepA == null){
                        this.loser = "All";
                    }else if(nextstepB == null ){
                        this.loser = "B";
                    }else {
                        this.loser = "A";
                    }
                }finally{
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }

    private void sendResult() {
        JSONObject message = new JSONObject();
        message.set("event","result");
        message.set("loser",this.loser);
        System.out.println("loser:" + this.loser);
        sendMessageAll(message.toString());

    }

    private void sendMessageAll(String message) {
        WebSocketServer.users.get(this.playerA.getId()).sendMessage(message);
        WebSocketServer.users.get(this.playerB.getId()).sendMessage(message);
    }

    private void sendMove() {
        lock.lock();
        try{
            JSONObject message = new JSONObject();
            message.set("event","move");
            message.set("a_direction",this.nextstepA);
            message.set("b_direction",this.nextstepB);
            sendMessageAll(message.toString());
            this.nextstepA=this.nextstepB=null;
        }finally{
            lock.unlock();
        }
    }

    private void judge() {
        List<Cell> cellsA = this.playerA.getCells();
        List<Cell> cellsB = this.playerB.getCells();
        boolean validA = check_valid(cellsA, cellsB);
        boolean validB = check_valid(cellsB,cellsA);
        if(!validA || !validB){
            this.status = "finished";
            if(!validA && !validB){
                this.loser =  "All";
            }else if(!validA){
                this.loser = "A";
            }else {
                this.loser = "B";
            }
        }

    }

    private boolean check_valid(List<Cell> A, List<Cell> B) {
        int n = A.size();
        Cell cell = A.get(n - 1);
        if (this.map[cell.getX()][cell.getY()] == 1) {
            return false;
        }
        for(int i=0 ; i<n-1;i++){
            if(A.get(i).getX() == cell.getX()&&A.get(i).getY() == cell.getY()
                    || B.get(i).getX() == cell.getX()&&B.get(i).getY() == cell.getY()) return false;
        }
        return true;
    }

    private boolean nextStep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        for(int i= 0; i<50;i++){
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if(nextstepA != null && nextstepB != null){
                        this.playerA.getSteps().add(nextstepA);
                        this.playerB.getSteps().add(nextstepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
        return false;
    }

    public void setNextstepA(Integer nextstep){
        lock.lock();
        try{
            this.nextstepA = nextstep;
        }finally {
            lock.unlock();
        }
    }

    public void setNextstepB(Integer nextstep){
        lock.lock();
        try{
            this.nextstepB  = nextstep;
        }finally {
            lock.unlock();
        }
    }

    public Game(Integer rows, Integer cols, Integer innerWallsCount,Integer id_a,Integer id_b){
        this.rows = rows;
        this.cols = cols;
        this.innerWallsCount = innerWallsCount;
        this.map = new int[this.rows][this.cols];
        this.playerA= new Player(id_a,this.rows-2,1,new ArrayList<>());
        this.playerB = new Player(id_b,1,this.cols-2,new ArrayList<>());

    }


    public void createMap(){
        for(int i=0;i<1000;i++){
            if(draw()) break;
        }
    }

    /**
     * 检测地图两点是否连通
     * @param sx 起点row
     * @param sy 起点col
     * @param ex 终点row
     * @param ey 终点col
     * @return  是否连通
     */

    private boolean checkConnectivity(int sx,int sy,int ex,int ey){
        //两点相同时结束
        if(sy==ey&&sx==ex) return true;
        //标记
        this.map[sx][sy]=1;
        //遍历偏移方向
        for(int i=0;i<4;i++){
            int x=sx+dx[i],y=sy+dy[i];
            //剪枝
            if(this.map[x][y]==0&&checkConnectivity(x,y,ex,ey)){
                this.map[sx][sy]=0;
                return true;
            }
        }
        //恢复现场
        this.map[sx][sy]=0;
        return false;
    }

    /**
     * 画游戏地图
     * @return 地图是否画成功
     */
    private boolean draw() {
        //填充左右两边界
        for(int i=0;i<this.cols;i++){
            this.map[0][i]=1;
            this.map[this.rows-1][i]=1;
        }
        //填充上下边界
        for(int i=0;i<this.rows;i++){
            this.map[i][0]=1;
            this.map[i][this.cols-1]=1;
        }
        //创建随机器
        Random random = new Random(System.currentTimeMillis());
        //已填充墙的数量
        int count =0;
        //开始填充障碍物(墙)
        while(count < this.innerWallsCount / 2){
            for(int i=0;i<1000;i++){
                //记录随机坐标
                int randRow = random.nextInt(this.rows);
                int randCol = random.nextInt(this.cols);
                //中心对称
                if(this.map[randRow][randCol] == 1 || this.map[this.rows-1-randRow][this.cols - 1 - randCol] == 1)
                    continue;
                //填充点不能玩家起始点
                if(randRow == this.rows-2 && randCol == 1 || randRow == 1 && randCol == this.cols - 2 ) continue;

                this.map[randRow][randCol] = this.map[this.rows - 1 - randRow ][this.cols - 1 - randCol ] = 1;
                break;
            }
            count++;
        }

        return checkConnectivity(this.rows - 2, 1, 1, this.cols - 2);
    }

}
