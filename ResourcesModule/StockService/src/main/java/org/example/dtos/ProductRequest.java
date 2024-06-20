package org.example.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Name can't be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @NotNull(message = "Type can't be null")
    private TypeEnum type;

    @NotNull(message = "Currency can't be null")
    private CurrencyEnum currency;

    @Positive(message = "Price must be positive")
    @NotNull(message = "Price can't be null")
    private float price;

    @NotNull(message = "BrandId can't be null")
    private UUID brandId;

}
