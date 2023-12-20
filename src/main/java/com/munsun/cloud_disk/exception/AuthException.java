package com.munsun.cloud_disk.exception;

public class AuthException extends Exception {
    public AuthException(long id) {
        super(""+id);
    }
}
