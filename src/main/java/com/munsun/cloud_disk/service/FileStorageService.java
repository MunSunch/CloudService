package com.munsun.cloud_disk.service;

import com.munsun.cloud_disk.dto.response.FileDtoOut;
import com.munsun.cloud_disk.exception.FileNotFoundException;
import com.munsun.cloud_disk.exception.UploadFileException;
import com.munsun.cloud_disk.model.File;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface FileStorageService {
    File getFile(String filename) throws FileNotFoundException;
    void addFile(String filename, MultipartFile file) throws UploadFileException;
    void removeFile(String filename) throws FileNotFoundException;
    void putFile(String oldName, String newName) throws FileNotFoundException;
    List<FileDtoOut> getFiles(Integer limit);
}
