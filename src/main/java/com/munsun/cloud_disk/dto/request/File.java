package com.munsun.cloud_disk.dto.request;

import jakarta.validation.constraints.NotBlank;

public record File(
        @NotBlank(message = "file is empty")
        String file
) { }