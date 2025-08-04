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

import com.bibliosoft.library.entity.UserEntity;
import com.bibliosoft.library.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser() {
        UserEntity user = new UserEntity(null, "Juan Pérez", null);
        UserEntity savedUser = new UserEntity(1L, "Juan Pérez", null);

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserEntity result = userService.add(user);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("Juan Pérez", result.getName());
    }

    @Test
    public void testGetAllUsers() {
        List<UserEntity> users = Arrays.asList(
            new UserEntity(1L, "Ana López", null),
            new UserEntity(2L, "Luis García", null)
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals("Ana López", result.get(0).getName());
    }

    @Test
    public void testGetUserById() {
        UserEntity user = new UserEntity(1L, "Carlos Martínez", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Carlos Martínez", result.get().getName());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.getById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        boolean result = userService.delete(userId);

        assertTrue(result);
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.existsById(12345L)).thenReturn(false);

        boolean result = userService.delete(12345L);

        assertFalse(result);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
