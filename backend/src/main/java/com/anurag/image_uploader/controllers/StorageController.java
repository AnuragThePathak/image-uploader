package com.anurag.image_uploader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anurag.image_uploader.services.StorageService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/storage")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(service.upload(file));
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String name) {
        return ResponseEntity.ok(service.download(name));
    }

    @GetMapping
    public ResponseEntity<List<String>> listFiles() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> presignedURL(@PathVariable String name) {
        return ResponseEntity.ok(service.presignedURL(name));
    }

}
