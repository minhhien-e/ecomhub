package com.example.infrastructure.inboundHandler;

import com.example.application.usecase.LogService;

import com.example.infrastructure.dto.LogRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SALER','ADMIN')")
public class LogController {

    private final LogService logService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SALER','ADMIN')")
    public ResponseEntity<Void> createLog(@RequestBody LogRequest request) {
        logService.createLog(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
