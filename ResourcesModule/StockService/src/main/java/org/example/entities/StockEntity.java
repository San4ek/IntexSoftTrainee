package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock")
@Entity
public class StockEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private ColorEnum color;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private SizeEnum size;

    @JoinColumn(name = "product", referencedColumnName = "id")
    @OneToOne(optional = false)
    private ProductEntity product;

    @Column(name = "amount")
    private int amount;
}
