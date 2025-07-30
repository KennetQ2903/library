package com.bibliosoft.library.dto;

import org.junit.Test;
import static org.junit.Assert.*;

public class BookDTOTest {
    @Test
    public void testDefaultConstructorAndSetters() {
        BookDTO book = new BookDTO();

        book.setId(1L);
        book.setTitle("El Principito");
        book.setAuthorId(10L);
        book.setBorrowedByUserId(100L);
        book.setBorrowed(true);

        assertEquals(Long.valueOf(1), book.getId());
        assertEquals("El Principito", book.getTitle());
        assertEquals(Long.valueOf(10), book.getAuthorId());
        assertEquals(Long.valueOf(100), book.getBorrowedByUserId());
        assertTrue(book.isBorrowed());
    }

    @Test
    public void testParameterizedConstructor() {
        BookDTO book = new BookDTO(2L, "1984", 20L, 200L, false);

        assertEquals(Long.valueOf(2), book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals(Long.valueOf(20), book.getAuthorId());
        assertEquals(Long.valueOf(200), book.getBorrowedByUserId());
        assertFalse(book.isBorrowed());
    }

    @Test
    public void testToString() {
        BookDTO book = new BookDTO(3L, "Cien Años de Soledad", 30L, 300L, true);
        String expected = "BookDTO{id=3, title='Cien Años de Soledad', authorId=30, borrowedByUserId=300, borrowed=true}";

        assertEquals(expected, book.toString());
    }
}
