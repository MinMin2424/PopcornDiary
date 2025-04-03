package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.enums.MovieCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByIdAndUserId(Long id, Long userId);
    List<Movie> findByUserId(Long userId);
    List<Movie> findByUserIdAndYear(Long userId, Integer year);
    List<Movie> findByUserIdAndCountry(Long userId, MovieCountry country);

}
