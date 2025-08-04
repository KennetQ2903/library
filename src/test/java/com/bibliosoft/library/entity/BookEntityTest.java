package com.bibliosoft.library.entity;

import com.bibliosoft.library.repository.AuthorRepository;
import com.bibliosoft.library.repository.BookRepository;
import com.bibliosoft.library.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookEntityTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveBookWithAuthorAndUser() {
        AuthorEntity author = authorRepository.save(new AuthorEntity(null, "Mario Vargas Llosa", null));
        UserEntity user = userRepository.save(new UserEntity(null, "Luis Gómez", null));

        BookEntity book = new BookEntity(null, "La ciudad y los perros", author, user, true);
        BookEntity saved = bookRepository.save(book);

        assertNotNull(saved.getId());
        assertEquals("La ciudad y los perros", saved.getTitle());
        assertEquals(author.getId(), saved.getAuthor().getId());
        assertEquals(user.getId(), saved.getBorrowedBy().getId());
        assertTrue(saved.isBorrowed());
    }

    @Test
    public void testFindById() {
        AuthorEntity author = authorRepository.save(new AuthorEntity(null, "Laura Esquivel", null));
        BookEntity book = new BookEntity(null, "Como agua para chocolate", author, null, false);
        BookEntity saved = bookRepository.save(book);

        assertTrue(bookRepository.findById(saved.getId()).isPresent());
    }
}
