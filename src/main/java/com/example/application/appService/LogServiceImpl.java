package com.example.application.appService;

import com.example.application.usecase.LogService;
import com.example.domain.model.LogModel;
import com.example.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public void createLog(LogModel log) {
        if (log.getLogId() == null) {
            log.setLogId(UUID.randomUUID());
        }
        logRepository.save(log);
    }
}