/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long userId);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

}
