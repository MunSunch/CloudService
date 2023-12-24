package com.munsun.cloud_disk.service.impl;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.exception.UploadFileException;
import com.munsun.cloud_disk.mapping.FileMapper;
import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.repository.FileRepository;
import com.munsun.cloud_disk.service.FileStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class FileStorageImpl implements FileStorage {
    private final FileRepository repository;
    private final FileMapper fileMapper;

    @Override
    public File getFile(String filename) throws FileNotFoundException {
        return repository.findByName(filename)
                .orElseThrow(FileNotFoundException::new);
    }

    @Override
    public void addFile(String filename, MultipartFile file) throws UploadFileException {
        try {
            var bytes = file.getBytes();
            var newFile = File.builder()
                            .content(bytes)
                            .name(filename)
                            .type(file.getContentType())
                            .build();
            repository.save(newFile);
        } catch (IOException e) {
            log.error("Error reading");
            throw new UploadFileException(0, "Failed load file");
        }
    }

    @Override
    public void removeFile(String filename) throws FileNotFoundException {
        var file = repository.findByName(filename)
                        .orElseThrow(FileNotFoundException::new);
        repository.deleteById(file.getId());
    }

    @Override
    public void putFile(String oldName, String newName) throws FileNotFoundException {
        repository.replaceName(oldName, newName);
    }

    @Override
    public List<FileDtoOut> getFiles(Integer limit) {
        return repository.findAll(Pageable.ofSize(limit))
                .toList().stream()
                .map(fileMapper::toDtoOut)
                .collect(Collectors.toList());
    }
}
