package com.bibliosoft.library.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bibliosoft.library.repository.AuthorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorEntityTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAuthor() {
        AuthorEntity author = new AuthorEntity(null, "Gabriel García Márquez", null);
        AuthorEntity saved = authorRepository.save(author);

        assertNotNull(saved.getId());
        assertEquals("Gabriel García Márquez", saved.getName());
    }

    @Test
    public void testFindById() {
        AuthorEntity author = new AuthorEntity(null, "Isabel Allende", null);
        AuthorEntity saved = authorRepository.save(author);

        assertTrue(authorRepository.findById(saved.getId()).isPresent());
    }
}
