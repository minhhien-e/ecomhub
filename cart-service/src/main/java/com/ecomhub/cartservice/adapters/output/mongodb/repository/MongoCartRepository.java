package com.ecomhub.cartservice.adapters.output.mongodb.repository;

import com.ecomhub.cartservice.adapters.output.mongodb.document.CartDocument;
import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.port.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class MongoCartRepository implements CartRepository {

    private final SpringCartMongoRepository mongoRepo;

    @Override
    public Cart findByUserId(String userId) {
        return mongoRepo.findById(userId)
                .map(CartDocument::toDomain)
                .orElse(new Cart(userId, new ArrayList<>()));
    }

    @Override
    public void save(Cart cart) {
        mongoRepo.save(CartDocument.fromDomain(cart));
    }

}