/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.controller;

import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{userId}/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies(@PathVariable Long userId) {
        return movieService.getAllMovies(userId);
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable Long movieId,
                          @PathVariable Long userId) {
        return movieService.getMovie(movieId, userId);
    }

    @PostMapping
    public Movie addMovie(@PathVariable Long userId,
                          @RequestBody Movie movie) {
        return movieService.addMovie(movie, userId);
    }

    @PutMapping("/{movieId}")
    public Movie updateMovie(@PathVariable Long userId,
                             @PathVariable Long movieId,
                             @RequestBody Movie movie) {
        return movieService.updateMovie(movieId, movie, userId);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable Long movieId,
                            @PathVariable Long userId) {
        movieService.deleteMovie(movieId, userId);
    }
}
