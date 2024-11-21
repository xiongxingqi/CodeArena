package com.celest.matchingsystem.utils;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public static <T> @NotNull Result<T> success(){
        Result<T> res = new Result<>();
        res.setCode(1);
        res.setMessage("成功");
        return res;
    }

    public static <T> @NotNull Result<T> success(T data){
        Result<T> res = new Result<>();
        res.setCode(1);
        res.setMessage("成功");
        res.setData(data);
        return res;
    }

    public static <T> @NotNull Result<T> error(String message){
        Result<T> res = new Result<>();
        res.setCode(2);
        res.setMessage(message);
        return res;
    }
}
