/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.UserIdNotFoundException;
import com.funnyProjects.popcornDiary.exception.UserNotFoundException;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static com.funnyProjects.popcornDiary.environment.SetUserParameters.setUserParameters;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ComponentScan("com.funnyProjects.popcornDiary.service")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenGetUserById_WithExistingUser_thenReturnUser() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        User found = userService.getUserById(user.getId());
        assertEquals(user, found);
    }

    @Test
    public void whenGetUserById_WithNonExistingUser_thenReturnNull() {
        assertNull(userService.getUserById(-1L));
    }

    @Test
    public void whenCreateUser_WithValidData_thenReturnUser() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        User newUser = userService.createUser(user);
        assertNotNull(newUser);
        assertEquals(user, newUser);
        assertTrue(userRepository.existsById(newUser.getId()));
    }

    @Test
    public void whenUpdateUser_WithValidData_thenReturnUser() {
        User existingUser = new User(); setUserParameters(existingUser, "email@gmail.com");
        userRepository.save(existingUser);
        User updatedUser = new User(); setUserParameters(updatedUser, "email2@gmail.com");
        User user = userService.updateUser(existingUser.getId(), updatedUser);
        assertNotNull(user);
        assertEquals(updatedUser.getEmail(), user.getEmail());
    }

    @Test
    public void whenUpdateUser_WithNonExistingUser_thenThrowException() {
        User updatedUser = new User(); setUserParameters(updatedUser, "email2@gmail.com");
        assertThrows(UserIdNotFoundException.class, () ->
                userService.updateUser(999L, updatedUser));
    }
}
