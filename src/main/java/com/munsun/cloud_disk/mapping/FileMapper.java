package com.munsun.cloud_disk.mapping;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.model.File;

public interface FileMapper {
    FileDtoOut toDtoOut(File file);
}
