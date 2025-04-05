/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.ComicNotFoundException;
import com.funnyProjects.popcornDiary.exception.UserIdNotFoundException;
import com.funnyProjects.popcornDiary.model.Comic;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.repository.ComicRepository;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static com.funnyProjects.popcornDiary.environment.SetUserParameters.setUserParameters;
import static com.funnyProjects.popcornDiary.environment.SetComicParameters.setComicParameters;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ComponentScan("com.funnyProjects.popcornDiary.service")
public class ComicServiceTest {

    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComicService comicService;

    @Test
    public void whenGetAllComics_WithValidUserId_thenReturnComicsList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        Comic comic2 = new Comic(); setComicParameters(comic2, user);
        comicRepository.save(comic2);

        List<Comic> found = comicService.getAllComics(user.getId());
        assertEquals(2, found.size());
    }

    @Test
    public void whenGetAllComics_WithInvalidUserId_thenThrowException() {
        assertThrows(UserIdNotFoundException.class, () ->
                comicService.getAllComics(2L));
    }

    @Test
    public void whenGetAllComics_WithNoComics_thenReturnEmptyList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        List<Comic> found = comicService.getAllComics(user.getId());
        assertEquals(0, found.size());
    }

    @Test
    public void whenGetComic_WithValidId_thenReturnComic() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);

        Comic found = comicService.getComic(comic.getId(), user.getId());
        assertNotNull(found);
        assertEquals(comic.getId(), found.getId());
    }

    @Test
    public void whenGetComic_WithNonExistingComic_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertThrows(ComicNotFoundException.class, () ->
                comicService.getComic(200L, user.getId()));
    }

    @Test
    public void whenGetComic_WithNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        assertThrows(ComicNotFoundException.class, () ->
                comicService.getComic(comic.getId(), 200L));
    }

    @Test
    public void whenAddComic_WithValidData_thenReturnComic() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        Comic savedComic = comicService.addComic(comic, user.getId());
        assertNotNull(savedComic);
        assertEquals(comic.getId(), savedComic.getId());
        assertEquals(user.getId(), savedComic.getUser().getId());
    }

    @Test
    public void whenAddComic_WithNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        assertThrows(UserIdNotFoundException.class, () ->
                comicService.addComic(comic, 200L));
    }

    @Test
    public void whenUpdateComic_WithValidData_thenReturnComic() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        Comic updatedComic = new Comic(); setComicParameters(updatedComic, user);
        updatedComic.setTitle("New Title");
        Comic resultComic = comicService.updateComic(comic.getId(), updatedComic, user.getId());
        assertNotNull(resultComic);
        assertEquals(updatedComic.getTitle(), resultComic.getTitle());
    }

    @Test
    public void whenUpdateComic_withNonExistingComic_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic updatedComic = new Comic(); setComicParameters(updatedComic, user);
        updatedComic.setTitle("New Title");
        assertThrows(ComicNotFoundException.class, () ->
                comicService.updateComic(200L, updatedComic, user.getId()));
    }

    @Test
    public void whenUpdateComic_withNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        Comic updatedComic = new Comic(); setComicParameters(updatedComic, user);
        updatedComic.setTitle("New Title");
        assertThrows(UserIdNotFoundException.class, () ->
                comicService.updateComic(comic.getId(), updatedComic, 200L));
    }

    @Test
    public void whenDeleteComic_WithValidId_thenReturnComic() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        comicService.deleteComic(comic.getId(), user.getId());
        assertFalse(comicRepository.existsById(comic.getId()));
    }

    @Test
    public void whenDeleteComic_withNonExistingComic_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertThrows(ComicNotFoundException.class, () ->
                comicService.deleteComic(999L, user.getId()));
    }

    @Test
    public void whenDeleteComic_WithNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Comic comic = new Comic(); setComicParameters(comic, user);
        comicRepository.save(comic);
        assertThrows(UserIdNotFoundException.class, () ->
                comicService.deleteComic(comic.getId(), 999L));
    }
}
