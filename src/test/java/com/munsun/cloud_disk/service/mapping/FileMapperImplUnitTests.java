package com.munsun.cloud_disk.service.mapping;

import com.munsun.cloud_disk.dto.response.FileDtoOut;
import com.munsun.cloud_disk.mapper.FileMapper;
import com.munsun.cloud_disk.mapper.impl.FileMapperImpl;
import com.munsun.cloud_disk.model.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMapperImplUnitTests {
    private FileMapper mapper = new FileMapperImpl();

    @Test
    public void successMappingFile2FileDtoOut() throws IOException {
        var bytes = Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg"));
        FileDtoOut expected = new FileDtoOut("paris", bytes.length);

        File file = new File(1, expected.filename(), bytes, "jpeg");
        var actualDtoOut = mapper.toDtoOut(file);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(expected.filename(), actualDtoOut.filename());
            Assertions.assertEquals(expected.size(), actualDtoOut.size());
        });
    }
}
