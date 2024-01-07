package com.munsun.cloud_disk.dto.response;

public record Error(
        String message,
        Integer id
) {}