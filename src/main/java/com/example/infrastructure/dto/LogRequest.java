package com.example.infrastructure.dto;



import com.example.domain.model.LogModel;

import java.time.LocalDateTime;
import java.util.UUID;


public class LogRequest {
    private UUID inventoryLogId;
    private String title;
    private String content;
    private String note;
    private LocalDateTime createdAt;

    public LogRequest(String createInventory, String content, String note, LocalDateTime now) {
        this.inventoryLogId = UUID.randomUUID();
        this.title = createInventory;
        this.content = content;
        this.note = note;
        this.createdAt = now;
    }

    public LogModel toDomain() {
        return new LogModel(inventoryLogId, title, content, note, createdAt);
    }

    public UUID getInventoryLogId() {
        return inventoryLogId;
    }

    public void setInventoryLogId(UUID inventoryLogId) {
        this.inventoryLogId = inventoryLogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}