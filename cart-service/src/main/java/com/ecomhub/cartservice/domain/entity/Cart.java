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

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
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
            if (i.getProductId().equals(item.getProductId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());

                if (i.getName() == null || i.getName().isEmpty()) {
                    i.setName(item.getName());
                }
                if (i.getImage() == null || i.getImage().isEmpty()) {
                    i.setImage(item.getImage());
                }
                return;
            }
        }

        items.add(item);
    }



    public boolean removeItem(String productId) {
        return items.removeIf(item -> item.getProductId().equals(productId));
    }

    public boolean updateItem(CartItem updatedItem) {
        for (CartItem item : items) {
            if (Objects.equals(item.getProductId(), updatedItem.getProductId())) {
                item.setQuantity(updatedItem.getQuantity());
                item.setPrice(updatedItem.getPrice());
                return true;
            }
        }
        return false;
    }

    public boolean hasProduct(String productId) {
        return items.stream().anyMatch(i -> i.getProductId().equals(productId));
    }

    public void clear() {
        items.clear();
    }
}
