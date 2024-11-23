package com.celest.matchingsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer userId ;
    private Integer rating;
    private Integer waitTime;

    public void autoIncrement(){
        this.waitTime++;
    }
}
