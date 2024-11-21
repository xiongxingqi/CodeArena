package com.celest.backend.exception;

public class BaseException extends RuntimeException{
    public BaseException(){
     super();
    }
    public BaseException(String errorMessage){
        super(errorMessage);
    }

}
