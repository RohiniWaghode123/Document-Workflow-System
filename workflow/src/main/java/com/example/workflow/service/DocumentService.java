package com.example.workflow.service;

import com.example.workflow.entity.Document;
import com.example.workflow.entity.Status;
import com.example.workflow.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Document upload(Document doc) {
        doc.setStatus(Status.PENDING);
        doc.setCreatedAt(LocalDateTime.now());
        return repository.save(doc);
    }

    public List<Document> getAll() {
        return repository.findAll();
    }

    public Document approve(Long id) {
        Document doc = repository.findById(id).orElseThrow();
        doc.setStatus(Status.APPROVED);
        return repository.save(doc);
    }
    public Document reject(Long id) {
        Document doc = repository.findById(id).orElseThrow();
        doc.setStatus(Status.REJECTED);
        return repository.save(doc);
    }
    public String uploadFile(MultipartFile file, String name){
        try{
            // Folder where files will be stored
           // String folderPath = "uploads/";
            String folderPath = System.getProperty("user.dir") + "/uploads/";

            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }
            //folderpath
            String filePath = folderPath + file.getOriginalFilename();

            file.transferTo(new File(filePath));
            // Save in DB
            Document doc = new Document();
            doc.setName(name);
            doc.setContent(filePath); // store path instead of text
            doc.setStatus(Status.PENDING);
            doc.setCreatedAt(java.time.LocalDateTime.now());

            repository.save(doc);
            return "File uploaded successfully";
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("File upload failed");
        }
    }
}
