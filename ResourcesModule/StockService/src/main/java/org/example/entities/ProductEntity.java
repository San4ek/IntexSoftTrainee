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

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEnum type;

    @JoinColumn(name = "brand", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BrandEntity brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyEnum currency;

    @Column(name = "price", nullable = false)
    private Double price;
}
