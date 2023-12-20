package com.munsun.cloud_disk.dto.in;

import jakarta.validation.constraints.NotBlank;

public record LoginPasswordDtoIn(
        @NotBlank
        String login,

        @NotBlank
        String password
) {}