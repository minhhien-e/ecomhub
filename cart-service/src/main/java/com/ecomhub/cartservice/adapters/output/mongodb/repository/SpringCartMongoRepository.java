package com.ecomhub.cartservice.adapters.output.mongodb.repository;

import com.ecomhub.cartservice.adapters.output.mongodb.document.CartDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringCartMongoRepository extends MongoRepository<CartDocument, String> {}