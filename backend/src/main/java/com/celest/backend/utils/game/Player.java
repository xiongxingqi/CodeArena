package com.celest.backend.utils.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Integer id;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps;

    private boolean check_tail_increasing(int step){
        if(step <= 10 ) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells(){
        ArrayList<Cell> cells = new ArrayList<>();
        int[] dx = {-1,0,1,0} ,dy = {0,1,0,-1};
        int x =this.sx,y = this.sy;
        int step=0;
        cells.add(new Cell(this.sx,this.sy));
        for (Integer d : steps) {
            x += dx[d];
            y += dy[d];
            cells.add(new Cell(x,y));
            if(!check_tail_increasing(++step)){
                cells.remove(0);
            }
        }

        return cells;
    }

}
