package org.example.services;

import org.example.dtos.CartItemRequest;
import org.example.dtos.CartItemResponse;
import org.example.dtos.CartRequest;
import org.example.entities.CartEntity;
import org.example.entities.CartItemEntity;

import java.util.UUID;

/**
 * Interface for shopping carts.
 */
public interface CartService {

    CartEntity createCart(CartRequest cartRequest);

    CartEntity getCart(UUID cartId);

    CartEntity updateCart(UUID cartId, CartRequest cartRequest);

    CartItemEntity addItemInCart(CartItemRequest cartItemRequest);

    void deleteItemFromCart(UUID cartId, UUID stockItemId);

    void deleteCart(UUID cartId);
}
