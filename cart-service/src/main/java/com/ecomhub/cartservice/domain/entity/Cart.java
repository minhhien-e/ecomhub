package com.ecomhub.cartservice.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private String userId;
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(String userId, List<CartItem> items) {
        this.userId = userId;
        this.items = (items != null) ? items : new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem item) {
        for (CartItem i : items) {
            if (Objects.equals(i.getProductId(), item.getProductId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }
}
