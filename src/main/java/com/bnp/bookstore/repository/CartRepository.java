package com.bnp.bookstore.repository;

import com.bnp.bookstore.model.Cart;
import com.bnp.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    Integer deleteByUser(User user);
}
