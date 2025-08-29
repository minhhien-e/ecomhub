package com.example.inventory.application.usecase;

import com.example.inventory.application.command.AddVariantRequest;

import java.util.UUID;

public interface AddVariantUseCase {
    void addVariant(AddVariantRequest request);


}
