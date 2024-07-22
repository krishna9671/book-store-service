package com.bnp.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
}