package com.bibliosoft.library.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliosoft.library.dto.BookDTO;
import com.bibliosoft.library.service.BookService;
import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAll();
    }

    @PostMapping
    public BookDTO add(BookDTO book) {
        return bookService.add(book);
    }

    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable Long id) {
        return bookService.getById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!bookService.delete(id)) {
            throw new RuntimeException("Book not found");
        }
    }
    
    @PostMapping("/{bookId}/borrow/{userId}")
    public BookDTO borrowBook(@PathVariable Long bookId, @PathVariable Long userId) {
        return bookService.borrowBook(bookId, userId);
    }
}
