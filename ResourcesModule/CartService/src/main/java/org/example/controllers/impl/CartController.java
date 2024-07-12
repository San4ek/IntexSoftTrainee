package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.CartOperationsController;
import org.example.dtos.*;
import org.example.mappers.CartMapper;
import org.example.mappers.CartUpdateMapper;
import org.example.mappers.CartWithItemsMapper;
import org.example.services.CartService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller for shopping carts.
 */
@RestController
@RequiredArgsConstructor
public class CartController implements CartOperationsController {

    private final CartMapper cartMapper;
    private final CartWithItemsMapper cartWithItemsMapper;
    private final CartUpdateMapper cartUpdateMapper;
    private final CartService cartService;

    /**
     * Retrieves the cart with its items by cart ID.
     *
     * @param cartId the ID of the cart to retrieve.
     * @return the cart with items response.
     */
    @Override
    public CartWithItemsResponse getCart(final UUID cartId) {
        return cartWithItemsMapper.toDto(cartService.getCart(cartId));
    }

    /**
     * Updates an existing cart.
     *
     * @param cartId the ID of the cart to update.
     * @param cartUpdateRequest the request containing updated cart details.
     * @return the response containing the updated cart details.
     */
    @Override
    public CartUpdateResponse updateCart(final UUID cartId, final CartUpdateRequest cartUpdateRequest) {
        return cartUpdateMapper.toDto(cartService.updateCart(cartId, cartUpdateRequest));
    }

    /**
     * Creates a new cart.
     *
     * @param cartRequest the request containing cart details.
     * @return the response containing the created cart details.
     */
    @Override
    public CartResponse createCart(final CartRequest cartRequest) {
        return cartMapper.toDto(cartService.createCart(cartRequest));
    }

    /**
     * Adds an item to the cart.
     *
     * @param cartItemRequest the request containing item details to add to the cart.
     */
    @Override
    public void addItemInCart(final CartItemRequest cartItemRequest) {
        cartService.addItemInCart(cartItemRequest);
    }

    /**
     * Deletes an item from the cart.
     *
     * @param cartId the ID of the cart.
     * @param stockItemId the ID of the stock item to delete from the cart.
     */
    @Override
    public void deleteItemFromCart(final UUID cartId, final UUID stockItemId) {
        cartService.deleteItemFromCart(cartId, stockItemId);
    }
}
