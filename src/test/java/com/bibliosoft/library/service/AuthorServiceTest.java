package com.bibliosoft.library.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.repository.AuthorRepository;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddAuthor() {
        AuthorEntity author = new AuthorEntity(null, "Gabriel García Márquez", null);
        AuthorEntity savedAuthor = new AuthorEntity(1L, "Gabriel García Márquez", null);

        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(savedAuthor);

        AuthorEntity result = authorService.add(author);

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());
        assertEquals("Gabriel García Márquez", result.getName());
    }

    @Test
    public void testGetAllAuthors() {
        List<AuthorEntity> authors = Arrays.asList(
            new AuthorEntity(1L, "Isabel Allende", null),
            new AuthorEntity(2L, "Mario Vargas Llosa", null)
        );

        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorEntity> result = authorService.getAll();

        assertEquals(2, result.size());
        assertEquals("Isabel Allende", result.get(0).getName());
    }

    @Test
    public void testGetAuthorById() {
        AuthorEntity author = new AuthorEntity(1L, "Julio Cortázar", null);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<AuthorEntity> result = authorService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Julio Cortázar", result.get().getName());
    }

    @Test
    public void testGetAuthorById_NotFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<AuthorEntity> result = authorService.getById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(authorId);

        boolean result = authorService.delete(authorId);

        assertTrue(result);
        verify(authorRepository).deleteById(authorId);
    }

    @Test
    public void testDeleteAuthor_NotFound() {
        when(authorRepository.existsById(999L)).thenReturn(false);

        boolean result = authorService.delete(999L);

        assertFalse(result);
        verify(authorRepository, never()).deleteById(anyLong());
    }
}
