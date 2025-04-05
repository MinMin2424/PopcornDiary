/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

import static com.funnyProjects.popcornDiary.environment.SetUserParameters.setUserParameters;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ComponentScan("com.funnyProjects.popcornDiary.repository")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenExistsById_WithExistingUser_thenReturnTrue() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    public void whenExistsById_WithNonExistingUser_thenReturnFalse() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertFalse(userRepository.existsById(2L));
    }

    @Test
    public void whenExistsByEmail_WithExistingUser_thenReturnTrue() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    public void whenExistsByEmail_WithNonExistingUser_thenReturnFalse() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        assertFalse(userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    public void whenFindByEmail_WithExistingUser_thenReturnUser() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
    }

    @Test
    public void whenFindByEmail_WithNonExistingUser_thenReturnEmpty() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        assertTrue(found.isEmpty());
    }
}
