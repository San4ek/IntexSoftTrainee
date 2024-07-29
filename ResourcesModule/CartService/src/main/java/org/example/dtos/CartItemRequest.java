package org.example.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    @NotNull
    private UUID cartId;

    @NotNull
    private UUID stockId;

    @NotNull
    private Long amount;
}
