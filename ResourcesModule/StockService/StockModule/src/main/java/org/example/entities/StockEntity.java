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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "StockModule")
@Entity
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne
    private TypeEntity type;

    @JoinColumn(name = "color", referencedColumnName = "id")
    @ManyToOne
    private ColorEntity color;

    @JoinColumn(name = "size", referencedColumnName = "id")
    @ManyToOne
    private SizeEntity size;

    @JoinColumn(name = "brand", referencedColumnName = "id")
    @ManyToOne
    private BrandEntity brand;

    @Column(name = "amount")
    private int amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private float price;

    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne
    private StatusEntity status;

    @Column(name = "createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "editedAt")
    @LastModifiedDate
    private LocalDateTime editedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name = "createdBy")
    @CreatedBy
    private String createdBy;

    @Column(name = "editedBy")
    @LastModifiedBy
    private String editedBy;

    @Column(name = "deletedBy")
    private String deletedBy;
}
