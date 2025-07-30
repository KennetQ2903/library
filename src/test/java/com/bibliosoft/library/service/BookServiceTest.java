package com.bibliosoft.library.service;
import org.junit.Before;
import org.junit.Test;

import com.bibliosoft.library.dto.BookDTO;


import static org.junit.Assert.*;
// https://www.baeldung.com/junit-assert
public class BookServiceTest {
    private BookService bookService;

    @Before
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    public void shouldAddBook() {
        BookDTO book = new BookDTO(null, "Test Book", 1L, null, false);
        BookDTO saved = bookService.add(book);

        assertNotNull(saved.getId());
        assertEquals("Test Book", saved.getTitle());
    }

    @Test
    public void shouldGetBookById() {
        BookDTO book = new BookDTO(null, "Another Book", 1L, null, false);
        BookDTO saved = bookService.add(book);

        BookDTO found = bookService.getById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    public void shouldBorrowBook() {
        BookDTO book = new BookDTO(null, "Borrow Me", 2L, null, false);
        BookDTO saved = bookService.add(book);

        BookDTO borrowed = bookService.borrowBook(saved.getId(), 99L);
        assertTrue(borrowed.isBorrowed());
        assertEquals(Long.valueOf(99), borrowed.getBorrowedByUserId());
    }
}
