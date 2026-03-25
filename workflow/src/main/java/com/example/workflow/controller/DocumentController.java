package com.example.workflow.controller;

import com.example.workflow.entity.Document;
import com.example.workflow.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping
    public Document upload(@RequestBody Document doc) {
        return service.upload(doc);
    }
    //file handling API
    @PostMapping("/upload-file")
    public String UploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("name") String name){
        return service.uploadFile(file, name);
    }

    @GetMapping
    public List<Document> getAll() {
        return service.getAll();
    }
    @PutMapping("/{id}/approve")
    public Document approve(@PathVariable Long id) {
        return service.approve(id);
    }
    @PutMapping("/{id}/reject")
    public Document reject(@PathVariable Long id) {
        return service.reject(id);
    }

}
