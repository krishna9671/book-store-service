package com.bnp.bookstore.service;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.CartItem;
import com.bnp.bookstore.model.User;

import com.bnp.bookstore.repository.BookRepository;
import com.bnp.bookstore.repository.CartItemRepository;
import com.bnp.bookstore.repository.CartRepository;
import com.bnp.bookstore.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Cart getCartByUsername(String username) {
        Optional<User> user = userRepository.findByusername(username);
        if (user.isPresent()) {
            return cartRepository.findByUser(user.get()).orElseGet(() -> {
                Cart cart = new Cart();
                cart.setUser(user.get());
                return cartRepository.save(cart);
            });
        }
        throw new IllegalArgumentException("User not found");
    }

    public Cart addItemToCart(String username, Long bookId, int quantity) {
    	Cart cart = getCartByUsername(username);
        Book book = bookRepository.findById(bookId)
                                  .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Optional<CartItem> existingCartItem = cart.getCartItems()
                                                  .stream()
                                                  .filter(item -> item.getBook().getId().equals(bookId))
                                                  .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }
        return cartRepository.save(cart);
    }

    public void removeItemFromCart(String username, Long bookId) {
        Cart cart = getCartByUsername(username);
        CartItem toRemove = null;
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getBook().getId().equals(bookId)) {
                toRemove = cartItem;
                break;
            }
        }
        if (toRemove != null) {
            cart.getCartItems().remove(toRemove);
            cartItemRepository.delete(toRemove);
            cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Item not found in cart");
        }
    }
    
    @Transactional
    public void removeCart(String username) {
    	Optional<User> user = userRepository.findByusername(username);
    	cartRepository.deleteByUser(user.get());
    }
}
