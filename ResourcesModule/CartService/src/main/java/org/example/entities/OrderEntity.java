package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.AddressEnum;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CartEntity cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "address")
    private AddressEnum address;

}
