/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.service;

import com.funnyProjects.popcornDiary.exception.MovieNotFoundException;
import com.funnyProjects.popcornDiary.exception.UserIdNotFoundException;
import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.model.enums.MovieCountry;
import com.funnyProjects.popcornDiary.repository.MovieRepository;
import com.funnyProjects.popcornDiary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static com.funnyProjects.popcornDiary.environment.SetUserParameters.setUserParameters;
import static com.funnyProjects.popcornDiary.environment.SetMovieParameters.setMovieParameters;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ComponentScan("com.funnyProjects.popcornDiary.service")
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieService movieService;

    @Test
    public void whenGetAllMovies_WithValidUserId_thenReturnMovieList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user);
        movieRepository.save(movie2);

        List<Movie> found = movieService.getAllMovies(user.getId());
        assertEquals(2, found.size());
    }

    @Test
    public void whenGetAllMovies_WithInvalidUserId_thenThrowException() {
        assertThrows(UserIdNotFoundException.class, () ->
                movieService.getAllMovies(2L));
    }

    @Test
    public void whenGetAllComics_WithNoComics_thenReturnEmptyList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        List<Movie> found = movieService.getAllMovies(user.getId());
        assertEquals(0, found.size());
    }

    @Test
    public void whenGetMovie_WithValidId_thenReturnMovie() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);

        Movie found = movieService.getMovie(movie.getId(), user.getId());
        assertNotNull(found);
        assertEquals(movie.getId(), found.getId());
    }

    @Test
    public void whenGetMovie_WithNonExistingMovie_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertThrows(MovieNotFoundException.class, () ->
                movieService.getMovie(200L, user.getId()));
    }

    @Test
    public void whenGetMovie_WithNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        assertThrows(MovieNotFoundException.class, () ->
                movieService.getMovie(movie.getId(), 200L));
    }

    @Test
    public void whenAddMovie_WithValidData_thenReturnMovie() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        Movie savedMovie = movieService.addMovie(movie, user.getId());
        assertNotNull(savedMovie);
        assertEquals(movie.getId(), savedMovie.getId());
        assertEquals(user.getId(), savedMovie.getUser().getId());
    }

    @Test
    public void whenAddMovie_WithNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        assertThrows(UserIdNotFoundException.class, () ->
                movieService.addMovie(movie, 200L));
    }

    @Test
    public void whenUpdateMovie_WithValidData_thenReturnMovie() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        Movie updatedMovie = new Movie(); setMovieParameters(updatedMovie, user);
        updatedMovie.setTitle("New Title");
        Movie resultMovie = movieService.updateMovie(movie.getId(), updatedMovie, user.getId());
        assertNotNull(resultMovie);
        assertEquals(updatedMovie.getTitle(), resultMovie.getTitle());
    }

    @Test
    public void whenUpdateComic_withNonExistingComic_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie updatedMovie = new Movie(); setMovieParameters(updatedMovie, user);
        updatedMovie.setTitle("New Title");
        assertThrows(MovieNotFoundException.class, () ->
                movieService.updateMovie(200L, updatedMovie, user.getId()));
    }

    @Test
    public void whenUpdateComic_withNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        Movie updatedMovie = new Movie(); setMovieParameters(updatedMovie, user);
        updatedMovie.setTitle("New Title");
        assertThrows(UserIdNotFoundException.class, () ->
                movieService.updateMovie(movie.getId(), updatedMovie, 200L));
    }

    @Test
    public void whenFilterMovieByYear_withMatchingMovies_thenReturnMovieList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, 2020);
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, 2024);
        movieRepository.save(movie2);
        Movie movie3 = new Movie(); setMovieParameters(movie3, user, 2024);
        movieRepository.save(movie3);

        List<Movie> found = movieService.filterMovieByYear(user.getId(), 2024);
        assertEquals(2, found.size());
        assertTrue(found.contains(movie2));
        assertTrue(found.contains(movie3));
    }

    @Test
    public void whenFilterMovieByYear_withNoMatchingMovies_thenReturnEmptyList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, 2020);
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, 2024);
        movieRepository.save(movie2);

        List<Movie> found = movieService.filterMovieByYear(user.getId(), 2021);
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFilterMovieByCountry_withMatchingMovies_thenReturnMovieList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, "China");
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, "Korea");
        movieRepository.save(movie2);
        Movie movie3 = new Movie(); setMovieParameters(movie3, user, "Thailand");
        movieRepository.save(movie3);

        List<Movie> found = movieService.filterMovieByCountry(user.getId(), "China");
        assertEquals(1, found.size());
        assertTrue(found.contains(movie));
    }

    @Test
    public void whenFilterMovieByCountry_withNoMatchingMovies_thenReturnEmptyList() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user,"China");
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, "Korea");
        movieRepository.save(movie2);

        List<Movie> found = movieService.filterMovieByCountry(user.getId(), "Thailand");
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenDeleteComic_WithValidId_thenReturnComic() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        movieService.deleteMovie(movie.getId(), user.getId());
        assertFalse(movieRepository.existsById(movie.getId()));
    }

    @Test
    public void whenDeleteComic_withNonExistingComic_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        assertThrows(MovieNotFoundException.class, () ->
                movieService.deleteMovie(999L, user.getId()));
    }

    @Test
    public void whenDeleteComic_WithNonExistingUser_thenThrowException() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        assertThrows(UserIdNotFoundException.class, () ->
                movieService.deleteMovie(movie.getId(), 999L));
    }
}
