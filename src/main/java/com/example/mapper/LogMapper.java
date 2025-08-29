package com.example.mapper;

import com.example.domain.model.LogModel;
import com.example.infrastructure.persistence.LogDocument;

public class LogMapper {
    public static LogDocument toDocument(LogModel log) {
        LogDocument doc = new LogDocument();
        doc.setLogId(log.getLogId());
        doc.setTitle(log.getTitle());
        doc.setContent(log.getContent());
        doc.setNote(log.getNote());
        doc.setCreatedAt(log.getCreatedAt());
        return doc;
    }

    public static LogModel toModel(LogDocument log) {
        return new LogModel(
                log.getLogId(),
                log.getTitle(),
                log.getContent(),
                log.getNote(),
                log.getCreatedAt()
        );
    }
}