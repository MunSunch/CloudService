package com.munsun.cloud_disk.service.mapping;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.mapping.FileMapper;
import com.munsun.cloud_disk.mapping.impl.FileMapperImpl;
import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.repository.FileRepository;
import com.munsun.cloud_disk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

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
