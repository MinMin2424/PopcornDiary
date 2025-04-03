/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.UserIdNotFound;
import com.funnyProjects.popcornDiary.exception.UserNotFound;
import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.repository.MovieRepository;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Transactional
    public Movie addMovie(Movie movie, Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new UserIdNotFound("User with id " + userId + " not found");
        }
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFound("User with id " + userId + " not found");
        }
        movie.setUser(user.get());
        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateMovie(Long movieId, Movie updatedMovie, Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new UserIdNotFound("User with id " + userId + " not found");
        }
        Movie existingMovie = movieRepository.findByIdAndUserId(movieId, userId)
                .orElseThrow();
    }

}
