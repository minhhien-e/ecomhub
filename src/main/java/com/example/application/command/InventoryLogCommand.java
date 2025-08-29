package com.example.application.command;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class InventoryLogCommand {

    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String content;
    @Setter
    @Getter
    private String note;
    private LocalDateTime createdAt;

    public InventoryLogCommand(String title, String content, String note, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.note = note;
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
