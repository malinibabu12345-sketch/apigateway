package com.guvi.apigateway.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message){

        super(message);
    }
}
