package com.munsun.cloud_disk.mapping.impl;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.mapping.FileMapper;
import com.munsun.cloud_disk.model.File;
import org.springframework.stereotype.Component;

@Component
public class FileMapperImpl implements FileMapper {
    @Override
    public FileDtoOut toDtoOut(File file) {
        return new FileDtoOut(file.getName(), file.getContent().length);
    }
}
