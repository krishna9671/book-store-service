package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam String username) {
        return ResponseEntity.ok(cartService.getCartByUsername(username));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestParam String username, @RequestParam Long bookId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(username, bookId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItemFromCart(@RequestParam String username, @RequestParam Long bookId) {
        cartService.removeItemFromCart(username, bookId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/removecart")
    public ResponseEntity<Void> removeCart(@RequestParam String username) {
        cartService.removeCart(username);
        return ResponseEntity.ok().build();
    }
}
