package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
public class ProductEntity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEnum type;

    @JoinColumn(name = "brand", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BrandEntity brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyEnum currency;

    @Column(name = "price")
    private float price;
}
