package com.munsun.cloud_disk.service.repository;

import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.repository.FileRepository;
import com.munsun.cloud_disk.service.PostgresContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
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

    @Test
    @Transactional
    public void successSearchFileByName() throws IOException {
        File file = new File();
            file.setId(null);
            file.setName("paris");
            file.setType("jpeg");
            file.setContent(Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")));

        fileRepository.save(file);
        var actualFile = fileRepository.findByName(file.getName());

        Assertions.assertTrue(actualFile.isPresent());
    }

    @Test
    @Transactional
    public void failedSearchFileByName() throws IOException {
        var actualFile = fileRepository.findByName("test-filename");

        Assertions.assertTrue(actualFile.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 1})
    @Transactional
    public void successFindAll(int limit) throws IOException {
        for(int i=0; i<limit*2; i++) {
            File file = new File();
                file.setId(null);
                file.setName("paris"+limit);
                file.setType("jpeg");
                file.setContent(Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")));

            fileRepository.save(file);
        }

        var actual = fileRepository.findAll(Pageable.ofSize(limit));

        Assertions.assertEquals(limit, actual.getTotalElements());
    }
}
