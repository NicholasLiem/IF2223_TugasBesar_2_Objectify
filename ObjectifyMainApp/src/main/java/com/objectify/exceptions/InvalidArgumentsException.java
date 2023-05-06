package com.objectify.exceptions;

public class InvalidArgumentsException extends CustomException {
    
    public InvalidArgumentsException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return super.getMessage();
    }
}
