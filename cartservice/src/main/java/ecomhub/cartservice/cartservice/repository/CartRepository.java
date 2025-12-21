package ecomhub.cartservice.cartservice.repository;

import ecomhub.cartservice.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    void removeCartById(UUID id);



}
