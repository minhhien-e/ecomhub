package ecomhub.cartservice.cartservice.service;

import ecomhub.cartservice.cartservice.exception.custom.MissingCustomerIdException;
import ecomhub.cartservice.cartservice.repository.CartItemRepository;
import ecomhub.cartservice.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // Cart
    @Transactional
    public void createCart(UUID customerId) {
        if (customerId == null) {
            throw new MissingCustomerIdException();
        }
        cartRepository.findById(customerId);
    }


}
