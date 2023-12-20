package com.munsun.cloud_disk.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class File {
    private byte[] content;
    private String type;
}
