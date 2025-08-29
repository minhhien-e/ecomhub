package com.example.inventory.application.usecase;


import com.example.inventory.application.command.UpdateInventoryRequest;
import jakarta.validation.Valid;

public interface UpdateInventoryUseCase {
    void update(@Valid UpdateInventoryRequest cmd);
}

