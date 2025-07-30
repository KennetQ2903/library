package com.bibliosoft.library.dto;

import org.junit.Test;
import static org.junit.Assert.*;

public class AuthorDTOTest {
    @Test
    public void testDefaultConstructorAndSetters() {
        AuthorDTO author = new AuthorDTO();

        author.setId(1L);
        author.setName("Gabriel García Márquez");

        assertEquals(Long.valueOf(1), author.getId());
        assertEquals("Gabriel García Márquez", author.getName());
    }

    @Test
    public void testParameterizedConstructor() {
        AuthorDTO author = new AuthorDTO(2L, "Isabel Allende");

        assertEquals(Long.valueOf(2), author.getId());
        assertEquals("Isabel Allende", author.getName());
    }

    @Test
    public void testToString() {
        AuthorDTO author = new AuthorDTO(3L, "Mario Vargas Llosa");
        String expected = "AuthorDTO{id=3, name='Mario Vargas Llosa'}";

        assertEquals(expected, author.toString());
    }
}
