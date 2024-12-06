package com.celest.botrunningsystem.runningbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bot implements java.util.function.Supplier<Integer>{

    private final static int[] dx = {-1,0,1,0},dy = {0,1,0,-1};

    public int move(String input) {
        String[] data = input.split("#");
        int[][] map = new int[13][14];
        for(int i=0,k=0;i<13;i++){
            for(int j=0;j<14;j++,k++){
                if(data[0].charAt(k) == '1'){
                    map[i][j] = 1;
                }
            }
        }
        int meSx = Integer.parseInt(data[1]);
        int meSy = Integer.parseInt(data[2]);
        int youSx = Integer.parseInt(data[4]);
        int youSy = Integer.parseInt(data[5]);

        List<Cell> meCells = getCells(meSx,meSy,data[3]);
        List<Cell> youCells = getCells(youSx, youSy, data[6]);

        for (Cell cell : meCells)  map[cell.x][cell.y] = 1;
        for (Cell cell : youCells)  map[cell.x][cell.y] = 1;

        Cell front = meCells.get(meCells.size() - 1);
        for(int i= 0 ;i<4;i++){
            int nextX=front.x + dx[i];
            int nextY=front.y + dy[i];
            if(nextX>0&&nextX < 13&&nextY>0&& nextY<14 &&map[nextX][nextY] == 0) return i;
        }

        return 0;
    }
    private boolean check_tail_increasing(int round){
        if(round <= 10) return true;

        return round % 3 == 1;
    }
    private List<Cell> getCells(int meSx, int meSy, String steps) {
        steps = steps.substring(1,steps.length() - 1);
        int round = 0;
        List<Cell> cells = new ArrayList<>();
        int sx = meSx,sy = meSy;
        cells.add(new Cell(sx, sy));
        for(int i=0;i<steps.length();i++ ){
            sx+=dx[steps.charAt(i) - '0'];
            sy+=dy[steps.charAt(i) - '0'];
            cells.add(new Cell(sx,sy));
            if(!check_tail_increasing(++round)) cells.remove(0);
        }

        return cells;
    }

    @Override
    public Integer get() {
        File file = new File("input.txt");
        int res =0;
        try {
            Scanner in = new Scanner(file);
            res = move(in.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    private record Cell(int x, int y) {
    }
}
