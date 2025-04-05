/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static com.funnyProjects.popcornDiary.environment.SetUserParameters.setUserParameters;
import static com.funnyProjects.popcornDiary.environment.SetMovieParameters.setMovieParameters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
@ComponentScan("com.funnyProjects.popcornDiary.repository")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByIdAndUserId_thenReturnMovie() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);

        Optional<Movie> found = movieRepository.findByIdAndUserId(movie.getId(), user.getId());
        assertTrue(found.isPresent());
        assertEquals(movie.getId(), found.get().getId());
        assertEquals(user.getId(), found.get().getUser().getId());
    }

    @Test
    public void whenFindByIdAndUserId_WithWrongUserId_thenReturnEmpty() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        User user2 = new User(); setUserParameters(user2, "email2@gmail.com");
        userRepository.save(user2);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);

        Optional<Movie> found = movieRepository.findByIdAndUserId(movie.getId(), user2.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFindByUserId_thenReturnListMovies() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user);
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user);
        movieRepository.save(movie2);

        List<Movie> found = movieRepository.findByUserId(user.getId());
        assertEquals(2, found.size());
    }

    @Test
    public void whenFindByUserId_WithNoMovies_thenReturnEmpty() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);

        List<Movie> found = movieRepository.findByUserId(user.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFindByUserIdAndYear_thenReturnListMovies() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, 2024);
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, 2017);
        movieRepository.save(movie2);
        Movie movie3 = new Movie(); setMovieParameters(movie3, user, 2020);
        movieRepository.save(movie3);

        List<Movie> found = movieRepository.findByUserIdAndYear(user.getId(), 2020);
        assertEquals(1, found.size());
        assertEquals(movie3.getId(), found.getFirst().getId());
    }

    @Test
    public void whenFindByUserIdAndYear_WithNoMatchingMovies_thenReturnListMovies() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, 2024);
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, 2017);
        movieRepository.save(movie2);
        Movie movie3 = new Movie(); setMovieParameters(movie3, user, 2020);
        movieRepository.save(movie3);

        List<Movie> found = movieRepository.findByUserIdAndYear(user.getId(), 2021);
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFindByUserIdAndCountry_thenReturnListMovies() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, "China");
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, "Korea");
        movieRepository.save(movie2);
        Movie movie3 = new Movie(); setMovieParameters(movie3, user, "China");
        movieRepository.save(movie3);

        List<Movie> found = movieRepository.findByUserIdAndCountry(user.getId(), "China");
        assertEquals(2, found.size());
    }

    @Test
    public void whenFindByUserIdAndCountry_WithNoMatchingMovies_thenReturnListMovies() {
        User user = new User(); setUserParameters(user, "email@gmail.com");
        userRepository.save(user);
        Movie movie = new Movie(); setMovieParameters(movie, user, "China");
        movieRepository.save(movie);
        Movie movie2 = new Movie(); setMovieParameters(movie2, user, "Korea");
        movieRepository.save(movie2);
        Movie movie3 = new Movie(); setMovieParameters(movie3, user, "China");
        movieRepository.save(movie3);

        List<Movie> found = movieRepository.findByUserIdAndCountry(user.getId(), "Japan");
        assertTrue(found.isEmpty());
    }

}
