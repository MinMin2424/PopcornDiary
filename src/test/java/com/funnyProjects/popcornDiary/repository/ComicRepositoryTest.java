/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.Comic;
import com.funnyProjects.popcornDiary.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static com.funnyProjects.popcornDiary.environment.SetUserParameters.setUserParameters;
import static com.funnyProjects.popcornDiary.environment.SetComicParameters.setComicParameters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ComponentScan("com.funnyProjects.popcornDiary.repository")
public class ComicRepositoryTest {

    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByIdAndUserId_thenReturnComic() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);

        Optional<Comic> found = comicRepository.findByIdAndUserId(comic.getId(), user.getId());
        assertTrue(found.isPresent());
        assertEquals(comic.getId(), found.get().getId());
        assertEquals(user.getId(), found.get().getUser().getId());
    }

    @Test
    public void whenFindByIdAndUserId_WithWrongUserId_thenReturnEmpty() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        User user2 = new User(); setUserParameters(user2, "email2@gmail.com");
        userRepository.save(user2);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);

        Optional<Comic> found = comicRepository.findByIdAndUserId(comic.getId(), user2.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFindByUserId_thenReturnComicsList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);

        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        Comic comic2 = new Comic(); setComicParameters(comic2, user);
        comicRepository.save(comic2);

        List<Comic> found = comicRepository.findByUserId(user.getId());
        assertEquals(2, found.size());
    }

    @Test
    public void whenFindByUserId_WithNoComics_thenReturnEmpty() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);

        List<Comic> found = comicRepository.findByUserId(user.getId());
        assertTrue(found.isEmpty());
    }
}
