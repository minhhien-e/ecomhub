
package com.feature.cartservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.feature.cartservice.helper.UUIDBinaryConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "cart_item")
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @Column
    @Convert(converter = UUIDBinaryConverter.class)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @ToString.Exclude
    @JsonBackReference
    private Cart cart;

    @Column(name = "variant_id", columnDefinition = "BINARY(16)")
    @Convert(converter = UUIDBinaryConverter.class)
    private UUID variantId;

    private int quantity;
    private double price;


    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void changeQuantity(int i) {
        this.quantity = i;
    }

    public CartItem(Cart cart, UUID variantId, int quantity, double price) {
        this.id = UUID.randomUUID();
        this.cart = cart;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }
}
