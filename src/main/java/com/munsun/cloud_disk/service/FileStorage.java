package com.munsun.cloud_disk.service;


import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.model.File;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorage {
    File getFile(String filename);
    void addFile(String filename, MultipartFile file);
    void removeFile(String filename);
    void putFile(String oldName, String newName);
    List<FileDtoOut> getFiles(Integer limit);
}
