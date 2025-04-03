/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.UserIdNotFound;
import com.funnyProjects.popcornDiary.exception.UserNotFound;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User updateUser) {
        checkUserId(userId);
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(updateUser.getEmail());
            existingUser.setPassword(updateUser.getPassword());
        } else {
            throw new UserNotFound("User with id " + userId + " not found");
        }
        return userRepository.save(existingUser);
    }

    private void checkUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserIdNotFound("User with id " + userId + " not found");
        }
    }
}
