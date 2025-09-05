package com.bibliosoft.library.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bibliosoft.library.entity.AuthorEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAndFindById() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Gabriel García Márquez");

        AuthorEntity saved = authorRepository.save(author);

        Optional<AuthorEntity> found = authorRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Gabriel García Márquez", found.get().getName());
    }

    @Test
    public void testExistsByName() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Isabel Allende");

        authorRepository.save(author);

        assertTrue(authorRepository.existsByName("Isabel Allende"));
        assertFalse(authorRepository.existsByName("Autor Inexistente"));
    }

    @Test
    public void testDeleteAuthor() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Mario Vargas Llosa");

        AuthorEntity saved = authorRepository.save(author);
        Long id = saved.getId();

        authorRepository.deleteById(id);

        Optional<AuthorEntity> found = authorRepository.findById(id);
        assertFalse(found.isPresent());
    }
}
