package org.example.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.example.controllers.CartOperationsController;
import org.example.dtos.*;
import org.example.exceptions.EndpointNotImplementedException;
import org.example.mappers.CartItemMapper;
import org.example.mappers.CartMapper;
import org.example.mappers.CartWithItemsMapper;
import org.example.services.CartService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller for managing shopping carts.
 */
@RestController
@RequiredArgsConstructor
public class CartController implements CartOperationsController {

    private final CartMapper cartMapper;
    private final CartWithItemsMapper cartWithItemsMapper;
    private final CartItemMapper cartItemMapper;
    private final CartService cartService;

    /**
     * Retrieve the details of a specific cart by its id.
     *
     * @param cartId the id of the cart to retrieve.
     * @return a {@link CartWithItemsResponse} containing the details of the cart and its items.
     */
    @Override
    public CartWithItemsResponse getCart(final UUID cartId) {
        return cartWithItemsMapper.toDto(cartService.getCart(cartId));
    }

    /**
     * Create a new cart.
     *
     * @param cartRequest the request object containing the details of the cart to create.
     * @return the created {@link CartResponse}.
     */
    @Override
    public CartResponse createCart(final CartRequest cartRequest) {
        return cartMapper.toDto(cartService.createCart(cartRequest));
    }

    /**
     * Update an existing cart.
     *
     * @param cartId the id of the cart to update.
     * @param cartRequest the request object containing the updated details of the cart.
     * @return the updated {@link CartResponse}.
     */
    @Override
    public CartResponse updateCart(final UUID cartId, final CartRequest cartRequest) {
        return cartMapper.toDto(cartService.updateCart(cartId, cartRequest));
    }

    /**
     * Add an item to the cart.
     *
     * @param cartItemRequest the request object containing the details of the item to add to the cart.
     * @return the added {@link CartItemResponse}.
     */
    @Override
    public CartItemResponse addItemInCart(final CartItemRequest cartItemRequest) {
        return cartItemMapper.toDto(cartService.addItemInCart(cartItemRequest));
    }

    /**
     * Delete an item from the cart by its id and the cart's id.
     *
     * @param cartId the id of the cart.
     * @param stockItemId the id of the item to delete from the cart.
     */
    @Override
    public void deleteItemFromCart(final UUID cartId, final UUID stockItemId) {
        cartService.deleteItemFromCart(cartId, stockItemId);
    }
}
