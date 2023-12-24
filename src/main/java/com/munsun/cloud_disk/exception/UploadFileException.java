package com.munsun.cloud_disk.exception;

public class UploadFileException extends Exception{
    public UploadFileException(int id, String message) {
        super(message);
    }
}
