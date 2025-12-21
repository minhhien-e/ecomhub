package ecomhub.cartservice.cartservice.service;

import ecomhub.cartservice.cartservice.exception.custom.CartItemNotFoundException;
import ecomhub.cartservice.cartservice.exception.custom.MissingCartIdException;
import ecomhub.cartservice.cartservice.exception.custom.MissingVariantIdException;
import ecomhub.cartservice.cartservice.model.CartItem;
import ecomhub.cartservice.cartservice.repository.CartItemRepository;
import ecomhub.cartservice.cartservice.repository.CartRepository;
//import ecomhub.cartservice.cartservice.service.external.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
//@Validated
@Slf4j
public class DeleteService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
//    private final ProductServiceClient productServiceClient;

    @Transactional
    public void removeItemFromCart(UUID cartId, UUID variantId) {
        if (cartId == null) throw new MissingCartIdException();
        if (variantId == null) throw new MissingVariantIdException();
        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndVariantId(cartId, variantId)
                .orElseThrow(() -> new CartItemNotFoundException(variantId));
        cartItemRepository.delete(cartItem);
    }


    @Transactional
    public void removeCart(UUID cartId) {
        if (cartId == null) throw new MissingCartIdException();
        cartRepository.deleteById(cartId);
    }


    @Transactional
    public void clearCart(UUID cartId) {
        if (cartId == null) throw new MissingCartIdException();
        cartItemRepository.removeCartItemByCart_Id(cartId);
    }

    @Transactional
    public void deleteCart(UUID cartId) {
        if (cartId == null) throw new MissingCartIdException();
        cartRepository.removeCartById(cartId);
    }
}
