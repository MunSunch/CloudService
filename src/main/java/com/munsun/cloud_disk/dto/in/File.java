package com.munsun.cloud_disk.dto.in;

import jakarta.validation.constraints.NotBlank;

public record File(
        @NotBlank
        String file
) { }