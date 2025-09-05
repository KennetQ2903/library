package com.bibliosoft.library.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bibliosoft.library.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testExistsByName_ReturnsTrueIfUserExists() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setName("Juan Pérez");
        userRepository.save(user);

        // Act
        boolean exists = userRepository.existsByName("Juan Pérez");

        // Assert
        assertTrue(exists);
    }

    @Test
    public void testExistsByName_ReturnsFalseIfUserDoesNotExist() {
        // Act
        boolean exists = userRepository.existsByName("NoExiste");

        // Assert
        assertFalse(exists);
    }
}
