package ecomhub.cartservice.cartservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ecomhub.cartservice.cartservice.helper.UUIDBinaryConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Table(name = "carts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column( )
    @Convert(converter = UUIDBinaryConverter.class)
    private UUID id;

//    @Column(name = "customerId", nullable = true)
//    private UUID customerId;

    @Column(name = "note", length = 255, nullable = true)
    private String note;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "total_price")
    private double totalPrice;

    public Cart(UUID customerId) {
        this.id =  customerId;
        this.totalPrice = 0;
        this.note =null;
    }


}