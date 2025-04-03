package com.funnyProjects.popcornDiary.repository;

import com.funnyProjects.popcornDiary.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Long> {

    List<Comic> findByUserId(Long userId);

}
