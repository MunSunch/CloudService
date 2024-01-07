package com.munsun.cloud_disk.exception;

public class AuthenticationException extends Exception{
    private static final String DEFAULT_MESSAGE = "Bad credentials";

    public AuthenticationException() {
        super(DEFAULT_MESSAGE);
    }
}
