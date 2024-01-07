package com.munsun.cloud_disk.service.impl;

import com.munsun.cloud_disk.dto.response.FileDtoOut;
import com.munsun.cloud_disk.exception.UploadFileException;
import com.munsun.cloud_disk.mapper.FileMapper;
import com.munsun.cloud_disk.model.File;
import com.munsun.cloud_disk.repository.FileRepository;
import com.munsun.cloud_disk.service.FileStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        log.info("get file {}", filename);
        return repository.findByName(filename)
                .orElseThrow(FileNotFoundException::new);
    }

    @Override
    public void addFile(String filename, MultipartFile file) throws UploadFileException {
        log.info("save new file {}", filename);
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
        log.info("delete file {}", filename);
        var file = repository.findByName(filename)
                        .orElseThrow(FileNotFoundException::new);
        repository.deleteById(file.getId());
    }

    @Transactional
    @Override
    public void putFile(String oldName, String newName) throws FileNotFoundException {
        log.info("replace old filename={} on new filename={}", oldName, newName);
        repository.replaceName(oldName, newName);
    }

    @Override
    public List<FileDtoOut> getFiles(Integer limit) {
        log.info("getting all files in the amount={}", limit);
        return repository.findAll(Pageable.ofSize(limit))
                .toList().stream()
                .map(fileMapper::toDtoOut)
                .collect(Collectors.toList());
    }
}
