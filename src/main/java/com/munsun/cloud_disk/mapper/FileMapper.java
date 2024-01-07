package com.munsun.cloud_disk.mapper;

import com.munsun.cloud_disk.dto.response.FileDtoOut;
import com.munsun.cloud_disk.model.File;

public interface FileMapper {
    FileDtoOut toDtoOut(File file);
}
