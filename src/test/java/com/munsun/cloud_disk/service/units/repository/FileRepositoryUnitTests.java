package com.munsun.cloud_disk.service.units.repository;

import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileRepositoryUnitTests extends PostgresContainer {
    @Autowired
    private FileRepository fileRepository;

    @Test
    @Transactional
    public void successSaveNewFile() throws IOException {
        File file = new File();
            file.setId(null);
            file.setName("paris");
            file.setType("jpeg");
            file.setContent(Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")));

        var actualId = fileRepository.save(file).getId();
        var actualFile = fileRepository.getReferenceById(actualId);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(actualFile.getId());
            Assertions.assertEquals(file.getName(), actualFile.getName());
            Assertions.assertEquals(file.getType(), actualFile.getType());
            Assertions.assertEquals(file.getContent(), actualFile.getContent());
        });
    }

    @Test
    @Transactional
    public void successDeleteFile() throws IOException {
        File file = new File();
            file.setId(null);
            file.setName("paris");
            file.setType("jpeg");
            file.setContent(Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")));

        var actualId = fileRepository.save(file).getId();
        fileRepository.deleteById(actualId);

        Assertions.assertThrowsExactly(JpaObjectRetrievalFailureException.class,
                () -> fileRepository.getReferenceById(actualId));
    }

    @Test
    @Transactional
    public void successUpdateFilename() throws IOException {
        String expectedFilename = "non-paris";
        File file = new File();
            file.setId(null);
            file.setName("paris");
            file.setType("jpeg");
            file.setContent(Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")));

        var actualId = fileRepository.save(file).getId();
        fileRepository.getReferenceById(actualId).setName(expectedFilename);
        var actualFilename = fileRepository.getReferenceById(actualId).getName();

        Assertions.assertEquals(expectedFilename, actualFilename);
    }
}
