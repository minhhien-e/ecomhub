package com.example.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Document(collection = "logs")
@NoArgsConstructor
@Getter
@Setter
public class LogDocument {
    @Id
    private UUID logId;
    private String title;
    private String content;
    private String note;
    private LocalDateTime createdAt;
}