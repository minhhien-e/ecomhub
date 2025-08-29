package com.example.inventory.infrastructure.client;

import com.example.inventory.infrastructure.dto.LogMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/logs")
    void logMessage(@RequestBody LogMessageDto logMessage);


}