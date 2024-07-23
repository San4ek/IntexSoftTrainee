package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private UUID id;

    private CartWithItemsResponse cart;

    private AddressResponse address;

    private Double cost;

    private Double convertedCost;
}
