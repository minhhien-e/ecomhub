package com.feature.cartservice.repository;

import com.feature.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    void removeCartById(UUID id);



}
