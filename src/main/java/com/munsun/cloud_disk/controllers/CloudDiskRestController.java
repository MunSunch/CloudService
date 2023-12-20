package com.munsun.cloud_disk.controllers;

import com.munsun.cloud_disk.dto.out.FileDtoOut;
import com.munsun.cloud_disk.service.FileStorage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CloudDiskRestController {
    private FileStorage storage;

    @GetMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getFile(@RequestParam String filename) {
        log.info("GET /file");
        return ResponseEntity
                .ok()
                .body(storage.getFile(filename));
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveFile(@RequestParam String filename,
                         @RequestBody MultipartFile file,
                         HttpServletRequest request)
    {
        log.info("POST /file");
        storage.addFile(filename, file);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam String filename) {
        log.info("DELETE /file");
        storage.removeFile(filename);
    }

    @PutMapping("/file")
    public void updateFile(@RequestParam String filename) {
        log.info("PUT /file");
    }

    @GetMapping(value = "/list")
    public List<FileDtoOut> getFiles(@RequestParam Integer limit) {
        log.info("GET /list");
        return storage.getFiles(limit);
    }
}