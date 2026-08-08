package com.mishalp789.auth_service.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email){
        super("User already exists with email: "+email);
    }
}
