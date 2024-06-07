package entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BrandModule")
@Entity
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "brandName")
    private String brandName;
}
