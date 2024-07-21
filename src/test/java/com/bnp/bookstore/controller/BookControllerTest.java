package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.Book;
import com.bnp.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetAllBooks() {
        // Arrange
        Book book1 = new Book(1L, "Book Title 1", "Author 1", 10.99);
        Book book2 = new Book(2L, "Book Title 2", "Author 2",  12.99);
        List<Book> mockBooks = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(mockBooks);

        // Act
        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockBooks, response.getBody());
    }
}
