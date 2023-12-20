package com.munsun.cloud_disk.dto.in;

import java.util.Arrays;

public record File(
        String hash,
        String file
) { }