package com.bibliosoft.library.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.entity.BookEntity;
import com.bibliosoft.library.entity.UserEntity;
import com.bibliosoft.library.repository.AuthorRepository;
import com.bibliosoft.library.repository.BookRepository;
import com.bibliosoft.library.repository.UserRepository;

public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private AuthorEntity author;
    private UserEntity user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        author = new AuthorEntity(1L, "Author Name", null);
        user = new UserEntity(99L, "User Name", null);
    }

    @Test
    public void shouldAddBook() {
        BookEntity bookToSave = new BookEntity(null, "Test Book", author, null, false);
        BookEntity savedBook = new BookEntity(1L, "Test Book", author, null, false);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedBook);

        BookEntity result = bookService.add(bookToSave);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals(author.getId(), result.getAuthor().getId());
    }

    @Test
    public void shouldGetBookById() {
        BookEntity book = new BookEntity(2L, "Another Book", author, null, false);

        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        Optional<BookEntity> result = bookService.getById(2L);

        assertTrue(result.isPresent());
        assertEquals("Another Book", result.get().getTitle());
    }

    @Test
    public void shouldBorrowBook() {
        BookEntity book = new BookEntity(3L, "Borrow Me", author, null, false);
        BookEntity borrowed = new BookEntity(3L, "Borrow Me", author, user, true);

        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));
        when(userRepository.findById(99L)).thenReturn(Optional.of(user));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(borrowed);

        BookEntity result = bookService.borrowBook(3L, 99L);

        assertTrue(result.isBorrowed());
        assertEquals(user.getId(), result.getBorrowedBy().getId());
    }
}