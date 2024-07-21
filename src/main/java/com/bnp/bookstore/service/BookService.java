package com.bnp.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	//Get list of all books present in database
	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return books;
	}
	
}