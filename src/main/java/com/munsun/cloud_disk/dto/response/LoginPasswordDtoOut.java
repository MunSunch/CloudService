package com.munsun.cloud_disk.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginPasswordDtoOut(
        @JsonProperty("auth-token")
        String authToken
) {}