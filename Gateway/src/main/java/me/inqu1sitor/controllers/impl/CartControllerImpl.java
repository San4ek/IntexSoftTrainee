package me.inqu1sitor.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.inqu1sitor.clients.CartClient;
import me.inqu1sitor.controllers.CartController;
import me.inqu1sitor.dtos.CartItemRequest;
import me.inqu1sitor.dtos.CartRequest;
import me.inqu1sitor.exceptions.EndpointNotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartClient cartClient;

    @Override
    public ResponseEntity<Object> getCart(final UUID cartId) {
        return cartClient.getCart(cartId);
    }

    @Override
    public ResponseEntity<Object> createCart(final CartRequest cartRequest) {
        return cartClient.createCart(cartRequest);
    }

    @Override
    public ResponseEntity<Object> updateCart(final UUID cartId, final CartRequest cartRequest) {
        return cartClient.updateCart(cartId, cartRequest);
    }

    @Override
    public ResponseEntity<Object> addItemInCart(final CartItemRequest cartItemRequest) {
        return cartClient.addItemInCart(cartItemRequest);
    }

    @Override
    public ResponseEntity<Object> deleteItemFromCart(final UUID cartId, final UUID stockItemId) {
        return cartClient.deleteItemFromCart(cartId, stockItemId);
    }
}
