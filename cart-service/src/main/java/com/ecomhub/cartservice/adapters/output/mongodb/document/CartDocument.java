package com.ecomhub.cartservice.adapters.output.mongodb.document;

import com.ecomhub.cartservice.domain.entity.Cart;
import com.ecomhub.cartservice.domain.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDocument {
    @Id
    private String userId;
    private List<CartItem> items;

    public static CartDocument fromDomain(Cart cart) {
        return new CartDocument(cart.getUserId(), cart.getItems());
    }

    public Cart toDomain() {
        return new Cart(userId, items);
    }
}
