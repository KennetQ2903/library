package com.bibliosoft.library.service;

import com.bibliosoft.library.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testAddUser() {
        UserDTO user = new UserDTO();
        user.setName("Juan Pérez");

        UserDTO saved = userService.add(user);

        assertNotNull(saved.getId());
        assertEquals("Juan Pérez", saved.getName());
        assertEquals(1, userService.getAll().size());
    }

    @Test
    void testGetAllUsers() {
        UserDTO user1 = new UserDTO();
        user1.setName("Ana López");

        UserDTO user2 = new UserDTO();
        user2.setName("Luis García");

        userService.add(user1);
        userService.add(user2);

        List<UserDTO> allUsers = userService.getAll();
        assertEquals(2, allUsers.size());
    }

    @Test
    void testGetUserById() {
        UserDTO user = new UserDTO();
        user.setName("Carlos Martínez");

        UserDTO saved = userService.add(user);
        Optional<UserDTO> found = userService.getById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Carlos Martínez", found.get().getName());
    }

    @Test
    void testGetUserById_NotFound() {
        Optional<UserDTO> result = userService.getById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteUser() {
        UserDTO user = new UserDTO();
        user.setName("María Fernández");

        UserDTO saved = userService.add(user);
        boolean deleted = userService.delete(saved.getId());

        assertTrue(deleted);
        assertEquals(0, userService.getAll().size());
    }

    @Test
    void testDeleteUser_NotFound() {
        boolean result = userService.delete(12345L);
        assertFalse(result);
    }
}
