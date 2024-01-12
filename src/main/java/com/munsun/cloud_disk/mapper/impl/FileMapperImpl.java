package com.munsun.cloud_disk.mapper.impl;

import com.munsun.cloud_disk.dto.response.FileDtoOut;
import com.munsun.cloud_disk.mapper.FileMapper;
import com.munsun.cloud_disk.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileMapperImpl implements FileMapper {
    @Override
    public FileDtoOut toDtoOut(File file) {
        log.info("mapping File to FileDtoOut");
        return new FileDtoOut(file.getName(), file.getContent().length);
    }
}
