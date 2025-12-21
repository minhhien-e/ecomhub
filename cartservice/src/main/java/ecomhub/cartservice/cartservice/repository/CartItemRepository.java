package ecomhub.cartservice.cartservice.repository;

import ecomhub.cartservice.cartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    void removeCartItemById(UUID id);

    Optional<CartItem> findCartItemByCartIdAndVariantId(UUID cartId, UUID variantId);
    @Modifying
    @Transactional
    void removeCartItemByCart_Id(UUID cartId);



}
