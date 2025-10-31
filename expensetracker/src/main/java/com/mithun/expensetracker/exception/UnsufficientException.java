package com.mithun.expensetracker.exception;

public class UnsufficientException extends RuntimeException{

    public UnsufficientException(String message){
        super(message);
    }
}
