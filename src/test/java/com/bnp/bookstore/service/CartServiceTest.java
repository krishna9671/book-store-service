package com.bnp.bookstore.service;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartItem;
import com.bnp.bookstore.model.User;
import com.bnp.bookstore.repository.BookRepository;
import com.bnp.bookstore.repository.CartItemRepository;
import com.bnp.bookstore.repository.CartRepository;
import com.bnp.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testGetCartByUsername_UserExists() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        Cart cart = new Cart();
        cart.setUser(user);

        when(userRepository.findByusername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        // Act
        Cart result = cartService.getCartByUsername(username);

        // Assert
        assertEquals(cart, result);
    }

    @Test
    public void testGetCartByUsername_UserDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";

        when(userRepository.findByusername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> cartService.getCartByUsername(username));
    }

    @Test
    public void testAddItemToCart_ExistingItem() {
        // Arrange
        String username = "testUser";
        Long bookId = 1L;
        int quantity = 3;

        User user = new User();
        user.setUsername(username);
        Cart cart = new Cart();
        cart.setUser(user);
        Book book = new Book(1L, "Book 1", "Author 1", 9.99);
        CartItem existingCartItem = new CartItem();
        existingCartItem.setBook(book);
        existingCartItem.setQuantity(1);
        cart.setCartItems(Collections.singletonList(existingCartItem));

        when(userRepository.findByusername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Act
        Cart result = cartService.addItemToCart(username, bookId, quantity);

        // Assert
        assertEquals(cart, result);
        assertEquals(quantity, existingCartItem.getQuantity());
        verify(cartItemRepository, times(1)).save(existingCartItem);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testRemoveItemFromCart_ItemDoesNotExist() {
        // Arrange
        String username = "testUser";
        Long bookId = 1L;

        User user = new User();
        user.setUsername(username);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(Collections.emptyList());

        when(userRepository.findByusername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> cartService.removeItemFromCart(username, bookId));
    }

    @Test
    public void testRemoveCart() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByusername(username)).thenReturn(Optional.of(user));

        // Act
        cartService.removeCart(username);

        // Assert
        verify(cartRepository, times(1)).deleteByUser(user);
    }
}
