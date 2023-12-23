package com.munsun.cloud_disk.exception;

import lombok.Getter;

@Getter
public class AuthException extends Exception {
    private long id;

    public AuthException(long id, String message) {
        super(message);
        this.id = id;
    }
}
