package com.bibliosoft.library.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.bibliosoft.library.dto.BookDTO;

@Service
public class BookService {
    private final Map<Long, BookDTO> books = new HashMap<>();
    private Long idCounter = 1L;

    public List<BookDTO> getAll() {
        return new ArrayList<>(books.values());
    }

    public BookDTO add(BookDTO book) {
        book.setId(idCounter++);
        books.put(book.getId(), book);
        return book;
    }

    public Optional<BookDTO> getById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public boolean delete(Long id) {
        return books.remove(id) != null;
    }

    public BookDTO borrowBook(Long bookId, Long userId) {
        BookDTO book = books.get(bookId);
        if (book != null && !book.isBorrowed()) {
            book.setBorrowed(true);
            book.setBorrowedByUserId(userId);
        }
        return book;
    }
}
