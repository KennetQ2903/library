package com.bibliosoft.library.service;

import com.bibliosoft.library.dto.AuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorService();
    }

    @Test
    void testAddAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName("Gabriel García Márquez");

        AuthorDTO saved = authorService.add(author);

        assertNotNull(saved.getId());
        assertEquals("Gabriel García Márquez", saved.getName());
        assertEquals(1, authorService.getAll().size());
    }

    @Test
    void testGetAllAuthors() {
        AuthorDTO author1 = new AuthorDTO();
        author1.setName("Isabel Allende");

        AuthorDTO author2 = new AuthorDTO();
        author2.setName("Mario Vargas Llosa");

        authorService.add(author1);
        authorService.add(author2);

        List<AuthorDTO> allAuthors = authorService.getAll();
        assertEquals(2, allAuthors.size());
    }

    @Test
    void testGetAuthorById() {
        AuthorDTO author = new AuthorDTO();
        author.setName("Julio Cortázar");

        AuthorDTO saved = authorService.add(author);
        Optional<AuthorDTO> found = authorService.getById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Julio Cortázar", found.get().getName());
    }

    @Test
    void testGetAuthorById_NotFound() {
        Optional<AuthorDTO> result = authorService.getById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteAuthor() {
        AuthorDTO author = new AuthorDTO();
        author.setName("Carlos Fuentes");

        AuthorDTO saved = authorService.add(author);
        boolean deleted = authorService.delete(saved.getId());

        assertTrue(deleted);
        assertEquals(0, authorService.getAll().size());
    }

    @Test
    void testDeleteAuthor_NotFound() {
        boolean result = authorService.delete(999L);
        assertFalse(result);
    }
}
