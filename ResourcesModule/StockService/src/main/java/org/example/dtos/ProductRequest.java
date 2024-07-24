package org.example.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.CurrencyEnum;
import org.example.enums.TypeEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull
    private String name;

    @NotNull
    private TypeEnum type;

    @NotNull
    private CurrencyEnum currency;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private UUID brandId;
}
