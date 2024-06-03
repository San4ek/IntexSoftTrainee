package entities;

import enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Stock")
@Entity
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    @Column(name = "brand")
    @Enumerated(EnumType.STRING)
    private BrandEnum brand;

    @Column(name = "amount")
    private int amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private float price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
