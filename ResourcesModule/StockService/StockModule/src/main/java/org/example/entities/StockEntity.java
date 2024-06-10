package org.example.entities;

import entities.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Stock")
@Entity
public class StockEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TypeEntity type;

    @JoinColumn(name = "color", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ColorEntity color;

    @JoinColumn(name = "size", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SizeEntity size;

    @JoinColumn(name = "brand", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BrandEntity brand;

    @Column(name = "amount")
    private int amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private float price;

    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StatusEntity status;

}
