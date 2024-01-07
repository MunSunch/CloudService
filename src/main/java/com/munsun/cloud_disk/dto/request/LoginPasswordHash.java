package com.munsun.cloud_disk.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginPasswordHash(
        @NotBlank
        @JsonProperty("name")
        String filename
) {}
