package org.example.services;

import jakarta.validation.Valid;
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

    CartEntity createCart(@Valid CartRequest cartRequest);

    CartEntity getCart(UUID cartId);

    CartEntity updateCart(UUID cartId, @Valid CartRequest cartRequest);

    CartItemEntity addItemInCart(@Valid CartItemRequest cartItemRequest);

    void deleteItemFromCart(UUID cartId, UUID stockItemId);

    void deleteCartItemsByStockId(UUID stockId);
}
