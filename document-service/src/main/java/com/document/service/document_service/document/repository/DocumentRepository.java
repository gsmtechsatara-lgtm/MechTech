package com.document.service.document_service.document.repository;

import com.document.service.document_service.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByReferenceIdAndModuleName(Long referenceId, String moduleName);

    List<Document> findByMerchantId(Long merchantId);
}

