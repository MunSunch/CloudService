package com.munsun.cloud_disk.service.impl;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.service.FileStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class FileStorageImpl implements FileStorage {
    private final Map<String, File> files = new HashMap<>();

    @Override
    public File getFile(String filename) {
        return files.get(filename);
    }

    @Override
    public void addFile(String filename, MultipartFile file) {
        try {
            var bytes = file.getBytes();
            var newFile = File.builder()
                            .content(bytes)
                            .type(file.getContentType())
                            .build();
            files.put(filename, newFile);
        } catch (IOException e) {
            log.error("Error reading");
        }
    }

    @Override
    public void removeFile(String filename) {
        files.remove(filename);
    }

    @Override
    public void putFile(String oldName, String newName) {
        File file = files.remove(oldName);
        files.put(newName, file);
    }

    @Override
    public List<FileDtoOut> getFiles(Integer limit) {
        return files.entrySet().stream()
                .map(x -> new FileDtoOut(x.getKey(), x.getValue().getContent().length))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
