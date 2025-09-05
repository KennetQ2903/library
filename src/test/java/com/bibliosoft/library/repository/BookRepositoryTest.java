package com.bibliosoft.library.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.entity.BookEntity;
import com.bibliosoft.library.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Gabriel García Márquez");
        author = authorRepository.save(author);

        BookEntity book = new BookEntity();
        book.setTitle("Cien Años de Soledad");
        book.setAuthor(author);
        book.setBorrowed(false);

        BookEntity saved = bookRepository.save(book);

        BookEntity found = bookRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Cien Años de Soledad", found.getTitle());
        assertEquals(author.getId(), found.getAuthor().getId());
    }

    @Test
    public void testFindByAuthorId() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Isabel Allende");
        author = authorRepository.save(author);

        BookEntity book1 = new BookEntity(null, "La Casa de los Espíritus", author, null, false);
        BookEntity book2 = new BookEntity(null, "Paula", author, null, false);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<BookEntity> books = bookRepository.findByAuthorId(author.getId());
        assertEquals(2, books.size());
    }

    @Test
    public void testFindByBorrowed() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Mario Vargas Llosa");
        author = authorRepository.save(author);

        BookEntity book1 = new BookEntity(null, "La Ciudad y los Perros", author, null, true);
        BookEntity book2 = new BookEntity(null, "Conversación en La Catedral", author, null, false);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<BookEntity> borrowedBooks = bookRepository.findByBorrowed(true);
        assertEquals(1, borrowedBooks.size());
        assertTrue(borrowedBooks.get(0).isBorrowed());
    }

    @Test
    public void testFindByBorrowedById() {
        AuthorEntity author = new AuthorEntity();
        author.setName("J.K. Rowling");
        author = authorRepository.save(author);

        UserEntity user = new UserEntity(null, "Juan Pérez", null);
        user = userRepository.save(user);

        BookEntity book1 = new BookEntity(null, "Harry Potter 1", author, user, true);
        BookEntity book2 = new BookEntity(null, "Harry Potter 2", author, null, false);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<BookEntity> booksBorrowedByUser = bookRepository.findByBorrowedById(user.getId());
        assertEquals(1, booksBorrowedByUser.size());
        assertEquals(user.getId(), booksBorrowedByUser.get(0).getBorrowedBy().getId());
    }

    @Test
    public void testDeleteBook() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Ernest Hemingway");
        author = authorRepository.save(author);

        BookEntity book = new BookEntity(null, "El Viejo y el Mar", author, null, false);
        book = bookRepository.save(book);

        bookRepository.deleteById(book.getId());

        assertFalse(bookRepository.findById(book.getId()).isPresent());
    }
}
