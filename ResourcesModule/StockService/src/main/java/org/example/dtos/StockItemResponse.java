package org.example.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.ColorEnum;
import org.example.enums.SizeEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockItemResponse {

    @NotNull(message = "Color can't be null")
    private ColorEnum color;

    @NotNull(message = "Size can't be null")
    private SizeEnum size;

    @NotNull(message = "ProductId can't be null")
    private UUID productId;

    @PositiveOrZero(message = "Amount can't be negative")
    @NotNull(message = "Amount can't be null")
    private Long amount;


}
