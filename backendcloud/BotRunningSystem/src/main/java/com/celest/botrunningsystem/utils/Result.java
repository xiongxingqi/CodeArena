package com.celest.botrunningsystem.utils;

import lombok.Data;

@Data
public class Result<T>{
    private int code;
    private String Message;
    private T data;

    public static  Result<?> success(){
        Result<?> res = new Result<>();
        res.setCode(1);
        res.setMessage("success");
        return res;
    }

    public static <T> Result<T> success(T data){
        Result<T> res = new Result<>();
        res.setCode(1);
        res.setMessage("success");
        res.setData(data);
        return res;
    }

    public static Result<?> error(String message){
        Result<?> res = new Result<>();
        res.setCode(2);
        res.setMessage(message);
        return res;
    }

}
