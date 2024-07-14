package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.CurrencyEnum;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
@ToString
@Entity
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyEnum currency;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems;

    public void addItems(CartItemEntity cartItem) {
        this.cartItems.add(cartItem);
    }

    public void removeItems(CartItemEntity cartItem) {
        this.cartItems.remove(cartItem);
    }

    public void removeAllItems() {
        this.cartItems.clear();
    }
}
