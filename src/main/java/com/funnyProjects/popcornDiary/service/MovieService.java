/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.MovieNotFound;
import com.funnyProjects.popcornDiary.exception.UserIdNotFound;
import com.funnyProjects.popcornDiary.exception.UserNotFound;
import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.model.enums.MovieCountry;
import com.funnyProjects.popcornDiary.repository.MovieRepository;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Movie> getAllMovies(Long userId) {
        return movieRepository.findByUserId(userId);
    }

    @Transactional
    public Movie getMovie(Long movieId, Long userId) {
        return movieRepository.findByIdAndUserId(movieId, userId)
                .orElseThrow(() -> new MovieNotFound("Movie with id " + movieId + " not found"));
    }

    @Transactional
    public Movie addMovie(Movie movie, Long userId) {
        checkUserId(userId);
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFound("User with id " + userId + " not found");
        }
        movie.setUser(user.get());
        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateMovie(Long movieId, Movie updatedMovie, Long userId) {
        checkUserId(userId);
        Movie existingMovie = movieRepository.findByIdAndUserId(movieId, userId)
                .orElseThrow(() -> new MovieNotFound("Movie with id " + movieId + " not found"));
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setYear(updatedMovie.getYear());
        existingMovie.setEpisodes(updatedMovie.getEpisodes());
        existingMovie.setCountry(updatedMovie.getCountry());
        existingMovie.setImagePath(updatedMovie.getImagePath());
        return movieRepository.save(existingMovie);
    }

    @Transactional
    public List<Movie> filterMovieByYear(Long userId, Integer year) {
        checkUserId(userId);
        return movieRepository.findByUserIdAndYear(userId, year);
    }

    @Transactional
    public List<Movie> filterMovieByCountry(Long userId, MovieCountry country) {
        checkUserId(userId);
        return movieRepository.findByUserIdAndCountry(userId, country);
    }

    @Transactional
    public void deleteMovie(Long movieId, Long userId) {
        checkUserId(userId);
        Movie movie = movieRepository.findByIdAndUserId(movieId, userId)
                .orElseThrow(() -> new MovieNotFound("Movie with id " + movieId + " not found"));
        movieRepository.delete(movie);
    }

    private void checkUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserIdNotFound("User with id " + userId + " not found");
        }
    }

}
