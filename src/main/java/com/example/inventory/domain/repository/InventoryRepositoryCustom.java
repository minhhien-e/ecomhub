package com.example.inventory.domain.repository;

import com.example.inventory.infrastructure.persistence.InventoryDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InventoryRepositoryCustom {
    Page<InventoryDocument> findByLocationIdAndActive(UUID locationId, boolean active, Pageable pageable);
}
