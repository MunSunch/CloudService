package com.munsun.cloud_disk.service;

import com.munsun.cloud_disk.dto.in.File;
import com.munsun.cloud_disk.dto.out.FileDtoOut;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorage {
    MultiValueMap<String, HttpEntity<?>> getFile(String filename);
    void addFile(String filename, MultipartFile file);
    void removeFile(String filename);
    List<FileDtoOut> getFiles(Integer limit);
}
