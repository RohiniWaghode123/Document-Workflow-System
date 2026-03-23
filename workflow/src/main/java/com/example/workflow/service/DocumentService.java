package com.example.workflow.service;

import com.example.workflow.entity.Document;
import com.example.workflow.entity.Status;
import com.example.workflow.repository.DocumentRepository;
import org.springframework.stereotype.Service;

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
}
