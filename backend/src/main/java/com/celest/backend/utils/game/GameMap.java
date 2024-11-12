package com.celest.backend.utils.game;

import lombok.Getter;

import java.util.Random;


public class GameMap {

    private final Integer rows;
    private final Integer cols;

    private final  Integer innerWallsCount;

    @Getter
    private final int[][] map;

    private final int[] dx={0,-1,0,1},dy={1,0,-1,0};

    public GameMap(Integer rows,Integer cols,Integer innerWallsCount){
        this.rows = rows;
        this.cols = cols;
        this.innerWallsCount = innerWallsCount;
        this.map = new int[this.rows][this.cols];
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
