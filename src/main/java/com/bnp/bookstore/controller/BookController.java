package com.bnp.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
		
	}
	
}