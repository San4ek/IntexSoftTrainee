package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.OrderClient;
import me.inqu1sitor.controllers.OrderController;
import me.inqu1sitor.dtos.OrderRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderClient orderClient;

    @Override
    public ResponseEntity<Object> getOrder(final UUID orderId) {
        return orderClient.getOrder(orderId);
    }

    @Override
    public ResponseEntity<Object> createOrder(final OrderRequest orderRequest) {
        return orderClient.createOrder(orderRequest);
    }

    @Override
    public ResponseEntity<Object> deleteOrder(final UUID orderId) {
        return orderClient.deleteOrder(orderId);
    }

    @Override
    public ResponseEntity<Object> deleteOrdersWithAddress(final UUID addressId) {
        return orderClient.deleteOrdersWithAddress(addressId);
    }
}
