package com.objectify.exceptions;

public class AppNotFoundException extends CustomException{
    public  AppNotFoundException(){
        super("App not found exception");
    }
    @Override
    public String getErrorCode() {
        return "App has not been initialized, start the App first!";
    }
}
