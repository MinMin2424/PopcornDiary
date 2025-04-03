package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.Comic;
import com.funnyProjects.popcornDiary.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComicRepository extends JpaRepository<Comic, Long> {

    Optional<Comic> findByIdAndUserId(Long id, Long userId);
    List<Comic> findByUserId(Long userId);

}
