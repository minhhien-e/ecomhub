package ecomhub.cart.service;

import ecomhub.cart.exception.custom.CartItemNotFoundException;
import ecomhub.cart.exception.custom.MissingCartIdException;
import ecomhub.cart.exception.custom.MissingVariantIdException;
import ecomhub.cart.model.Cart;
import ecomhub.cart.model.CartItem;
import ecomhub.cart.repository.CartItemRepository;
import ecomhub.cart.repository.CartRepository;
import ecomhub.cart.service.external.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReadService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;

    //Cart
    @Transactional
    public Cart getCart(UUID cartId) {
        if (cartId == null) throw new MissingCartIdException();
        return cartRepository.findById(cartId)
                .orElseGet(() -> {
                    log.info("User {} chưa có giỏ hàng, đang tạo mới...", cartId);
                    Cart newCart = new Cart();
                    newCart.setId(cartId);
                    newCart.setNote("Giỏ hàng mới");
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public CartItem getProductInfo(UUID variantId) {
        if (variantId == null) throw new MissingVariantIdException();
        productServiceClient.getProductInfo(variantId);
        return cartItemRepository.findById(variantId)
                .orElseThrow(() -> {
                    log.warn("Sản phẩm {} hợp lệ nhưng không tìm thấy trong giỏ hàng", variantId);
                    return new CartItemNotFoundException(variantId);
                });
    }
}
