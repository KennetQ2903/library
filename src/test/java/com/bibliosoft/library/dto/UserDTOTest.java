package com.bibliosoft.library.dto;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserDTOTest {
    @Test
    public void testDefaultConstructorAndSetters() {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("Juan");

        assertEquals(Long.valueOf(1), user.getId());
        assertEquals("Juan", user.getName());
    }

    @Test
    public void testParameterizedConstructor() {
        UserDTO user = new UserDTO(2L, "Ana");

        assertEquals(Long.valueOf(2), user.getId());
        assertEquals("Ana", user.getName());
    }

    @Test
    public void testToString() {
        UserDTO user = new UserDTO(3L, "Luis");
        String expected = "UserDTO{id=3, name='Luis'}";

        assertEquals(expected, user.toString());
    }
}
