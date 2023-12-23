package com.munsun.cloud_disk.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User not found!");
    }
}
