package com.munsun.cloud_disk.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginPasswordDtoIn(
        @NotBlank
        String login,

        @NotBlank
        String password
) {}