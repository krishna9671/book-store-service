package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    public void testGetCart() {
        // Arrange
        String username = "testUser";
        Cart mockCart = new Cart();
        when(cartService.getCartByUsername(username)).thenReturn(mockCart);

        // Act
        ResponseEntity<Cart> response = cartController.getCart(username);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }

    @Test
    public void testAddItemToCart() {
        // Arrange
        String username = "testUser";
        Long bookId = 1L;
        int quantity = 2;
        Cart mockCart = new Cart();
        when(cartService.addItemToCart(username, bookId, quantity)).thenReturn(mockCart);

        // Act
        ResponseEntity<Cart> response = cartController.addItemToCart(username, bookId, quantity);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCart, response.getBody());
    }

    @Test
    public void testRemoveItemFromCart() {
        // Arrange
        String username = "testUser";
        Long bookId = 1L;
        doNothing().when(cartService).removeItemFromCart(username, bookId);

        // Act
        ResponseEntity<Void> response = cartController.removeItemFromCart(username, bookId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(cartService, times(1)).removeItemFromCart(username, bookId);
    }

    @Test
    public void testRemoveCart() {
        // Arrange
        String username = "testUser";
        doNothing().when(cartService).removeCart(username);

        // Act
        ResponseEntity<Void> response = cartController.removeCart(username);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(cartService, times(1)).removeCart(username);
    }
}
