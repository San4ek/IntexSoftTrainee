package org.example.services;

import org.example.dtos.CartItemRequest;
import org.example.dtos.CartRequest;
import org.example.dtos.CartUpdateRequest;
import org.example.entities.CartEntity;

import java.util.UUID;

/**
 * Interface for shopping carts.
 */
public interface CartService {

    CartEntity createCart(CartRequest cartRequest);

    CartEntity getCart(UUID cartId);

    void addItemInCart(CartItemRequest cartItemRequest);

    void deleteItemFromCart(UUID cartId, UUID stockItemId);

    CartEntity updateCart(UUID cartId, CartUpdateRequest cartUpdateRequest);
}
