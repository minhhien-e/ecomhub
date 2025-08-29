package com.example.domain.repository;

import com.example.domain.model.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface LogRepository extends MongoRepository<LogModel, UUID> {

}

