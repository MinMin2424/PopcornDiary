/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.ComicNotFound;
import com.funnyProjects.popcornDiary.exception.MovieNotFound;
import com.funnyProjects.popcornDiary.exception.UserIdNotFound;
import com.funnyProjects.popcornDiary.exception.UserNotFound;
import com.funnyProjects.popcornDiary.model.Comic;
import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.repository.ComicRepository;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ComicService {

    private final ComicRepository comicRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Comic> getAllComics(Long userId) {
        return comicRepository.findByUserId(userId);
    }

    @Transactional
    public Comic getComic(Long comicId, Long userId) {
        return comicRepository.findByIdAndUserId(comicId, userId)
                .orElseThrow(() -> new ComicNotFound("Comic with id " + comicId + " not found"));
    }

    @Transactional
    public Comic addComic(Comic comic, Long userId) {
        checkUserId(userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFound("User with id " + userId + " not found");
        }
        comic.setUser(user.get());
        return comicRepository.save(comic);
    }

    @Transactional
    public Comic updateComic(Long comicId, Comic updatedComic, Long userId) {
        checkUserId(userId);
        Comic existingComic = comicRepository.findByIdAndUserId(comicId, userId)
                .orElseThrow(() -> new ComicNotFound("Comic with id " + comicId + " not found"));
        existingComic.setTitle(updatedComic.getTitle());
        existingComic.setChapters(updatedComic.getChapters());
        existingComic.setFinishedDate(updatedComic.getFinishedDate());
        existingComic.setImagePath(updatedComic.getImagePath());
        return comicRepository.save(existingComic);
    }

    @Transactional
    public void deleteComic(Long comicId, Long userId) {
        checkUserId(userId);
        Comic comic = comicRepository.findByIdAndUserId(comicId, userId)
                .orElseThrow(() -> new ComicNotFound("Comic with id " + comicId + " not found"));
        comicRepository.delete(comic);
    }

    private void checkUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserIdNotFound("User with id " + userId + " not found");
        }
    }
}
