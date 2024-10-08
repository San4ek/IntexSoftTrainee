package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private UUID id;

    private CartResponse cart;

    private AddressResponse address;

    private List<OrderItemResponse> orderItems;

    private Double totalCost;
}
