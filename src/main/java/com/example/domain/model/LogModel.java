package com.example.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;



public class LogModel {
    private UUID inventoryLogId;
    private String title;
    private String content;
    private String note;
    private LocalDateTime createdAt;

    public LogModel(UUID inventoryLogId, String title, String content, String note, LocalDateTime createdAt) {
        this.inventoryLogId = inventoryLogId;
        this.title = title;
        this.content = content;
        this.note = note;
        this.createdAt = createdAt;
    }

    public UUID getInventoryLogId() {
        return inventoryLogId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getLogId() {
        return inventoryLogId;
    }

    public void setLogId(UUID uuid) {
        this.inventoryLogId = uuid;
    }
}
