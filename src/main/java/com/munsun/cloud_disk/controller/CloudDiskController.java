package com.munsun.cloud_disk.controller;

import com.munsun.cloud_disk.dto.response.FileDtoOut;
import com.munsun.cloud_disk.dto.response.LoginPasswordHashDtoOut;
import com.munsun.cloud_disk.exception.UploadFileException;
import com.munsun.cloud_disk.service.FileStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CloudDiskController {
    private FileStorage storage;

    @GetMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getFile(@RequestParam String filename) throws FileNotFoundException {
        log.info("endpoint: GET /file");
        var file = storage.getFile(filename);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(file.getType()))
                .body(file.getContent());
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam String filename,
                         @RequestBody MultipartFile file) throws UploadFileException
    {
        log.info("endpoint: POST /file");
        storage.addFile(filename, file);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<Void> deleteFile(@RequestParam String filename) throws FileNotFoundException {
        log.info("endpoint: DELETE /file");
        storage.removeFile(filename);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/file")
    public void updateFile(@RequestParam("filename") String oldFilename,
                           @RequestBody LoginPasswordHashDtoOut loginPasswordHashDtoOut) throws FileNotFoundException
    {
        log.info("endpoint: PUT /file");
        storage.putFile(oldFilename, loginPasswordHashDtoOut.getFilename());
    }

    @GetMapping("/list")
    public List<FileDtoOut> getFiles(@RequestParam Integer limit) {
        log.info("endpoint: GET /list");
        return storage.getFiles(limit);
    }
}