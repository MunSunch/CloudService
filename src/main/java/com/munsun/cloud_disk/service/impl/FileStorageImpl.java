package com.munsun.cloud_disk.service.impl;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.service.FileStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class FileStorageImpl implements FileStorage {
    private final Map<String, byte[]> files = new HashMap<>();

    @Override
    public MultiValueMap<String, HttpEntity<?>> getFile(String filename) {
        var bytes = files.get(filename);
        var multipartFile = new MultipartBodyBuilder();
            multipartFile.part("hash", Arrays.hashCode(bytes));
            multipartFile.part("file", bytes);
        return multipartFile.build();
    }

    @Override
    public void addFile(String filename, MultipartFile file) {
        try {
            files.put(filename, file.getInputStream().readAllBytes());
        } catch (IOException e) {
            log.error("Error reading");
        }
    }

    @Override
    public void removeFile(String filename) {
        files.remove(filename);
    }

    @Override
    public List<FileDtoOut> getFiles(Integer limit) {
        return files.entrySet().stream()
                .map(x -> new FileDtoOut(x.getKey(), x.getValue().length))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
