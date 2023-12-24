package com.munsun.cloud_disk.service.services.integration;

import com.munsun.cloud_disk.exception.UploadFileException;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.repository.FileRepository;
import com.munsun.cloud_disk.service.FileStorage;
import com.munsun.cloud_disk.service.PostgresContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class FileStorageServiceIntegrationTests extends PostgresContainer {
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private FileRepository fileRepository;

    private File file;

    public FileStorageServiceIntegrationTests() throws IOException {
        file = new File(null,
                "paris",
                Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")),
                "image/jpeg");
    }

    @Test
    @Transactional
    public void successGetFile() throws FileNotFoundException {
        fileRepository.save(file);
        var actualFile = fileStorage.getFile(file.getName());
        Assertions.assertNotNull(actualFile.getId());
    }

    @Test
    @Transactional
    public void failedGetNotExistsFile() throws FileNotFoundException {
        Assertions.assertThrowsExactly(FileNotFoundException.class, ()-> {
            fileStorage.getFile(file.getName());
        });
    }

    @Test
    @Transactional
    public void successAddFile() throws UploadFileException {
        var multipart = new MockMultipartFile(file.getName(),
                file.getName()+".jpeg",
                "image/jpeg",
                file.getContent());

        fileStorage.addFile(file.getName(), multipart);
        var actualFile = fileRepository.findByName(file.getName());

        Assertions.assertTrue(actualFile.isPresent());
        Assertions.assertAll(() -> {
            Assertions.assertNotNull(actualFile.get().getId());
            Assertions.assertEquals(file.getName(), actualFile.get().getName());
            Assertions.assertEquals(file.getType(), actualFile.get().getType());
            Assertions.assertEquals(file.getContent(), actualFile.get().getContent());
        });
    }

    @Test
    @Transactional
    public void successRemoveFile() throws FileNotFoundException {
        fileRepository.save(file);
        fileStorage.removeFile(file.getName());
        var actualFile = fileRepository.findByName(file.getName());

        Assertions.assertTrue(actualFile.isEmpty());
    }

    @Test
    @Transactional
    public void failedRemoveNotExistsFile() throws FileNotFoundException {
        Assertions.assertThrowsExactly(FileNotFoundException.class, () ->
            fileStorage.removeFile(file.getName())
        );
    }

    @Test
    @Transactional
    public void successPutFilename() throws FileNotFoundException, UserNotFoundException {
        fileRepository.save(file);
        String oldName = file.getName();
        String newName = "new" + file.getName();

        fileStorage.putFile(oldName, newName);
        var actualFile = fileRepository.findByName(newName);

        Assertions.assertTrue(actualFile.isPresent());
        Assertions.assertEquals(newName, actualFile.get().getName());
    }

    @Test
    @Transactional
    public void failedPutNotExistsFile() throws FileNotFoundException, UserNotFoundException {
        String oldName = file.getName();
        String newName = "new" + file.getName();

        Assertions.assertThrowsExactly(FileNotFoundException.class, () -> {
            fileStorage.putFile(oldName, newName);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {3,1})
    @Transactional
    public void successGetFiles(int limit) throws IOException {
        for (int i = 0; i < limit; i++) {
            var file = new File(null,
                    "paris"+i,
                    Files.readAllBytes(Path.of("src/test/java/resources/static/paris.jpeg")),
                    "jpeg");

            fileRepository.save(file);
        }
        var actualSize = fileStorage.getFiles(limit).size();

        Assertions.assertEquals(limit, actualSize);
    }
}
















