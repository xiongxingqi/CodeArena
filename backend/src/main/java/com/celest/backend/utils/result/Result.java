package com.celest.backend.utils.result;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String errorMessage;
    private T data;

    public static <T> @NotNull Result<T> success(){
        Result<T> res = new Result<>();
        res.setCode(1);
        return res;
    }

    public static <T> @NotNull Result<T> success(T data){
        Result<T> res = new Result<>();
        res.setCode(1);
        res.setData(data);
        return res;
    }

    public static <T> @NotNull Result<T> error(String errorMessage){
        Result<T> res = new Result<>();
        res.setCode(2);
        res.setErrorMessage(errorMessage);
        return res;
    }
}
