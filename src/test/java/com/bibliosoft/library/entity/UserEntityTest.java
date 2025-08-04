package com.bibliosoft.library.entity;

import com.bibliosoft.library.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity(null, "Juan Pérez", null);
        UserEntity saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("Juan Pérez", saved.getName());
    }

    @Test
    public void testFindById() {
        UserEntity user = new UserEntity(null, "Ana Gómez", null);
        UserEntity saved = userRepository.save(user);

        assertTrue(userRepository.findById(saved.getId()).isPresent());
    }
}
