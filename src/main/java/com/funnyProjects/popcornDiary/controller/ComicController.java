/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.controller;

import com.funnyProjects.popcornDiary.model.Comic;
import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.service.ComicService;
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
@RequestMapping("/api/users/{userId}/comics")
public class ComicController {

    private final ComicService comicService;

    @GetMapping
    public List<Comic> getAllComics(@PathVariable Long userId) {
        return comicService.getAllComics(userId);
    }

    @GetMapping("/{comicId}")
    public Comic getComic(@PathVariable Long comicId,
                          @PathVariable Long userId) {
        return comicService.getComic(comicId, userId);
    }

    @PostMapping
    public Comic addComic(@PathVariable Long userId,
                          @RequestBody Comic comic) {
        return comicService.addComic(comic, userId);
    }

    @PutMapping("/{comicId}")
    public Comic updateComic(@PathVariable Long userId,
                             @PathVariable Long comicId,
                             @RequestBody Comic comic) {
        return comicService.updateComic(comicId, comic, userId);
    }

    @DeleteMapping("/{comicId}")
    public void deleteComic(@PathVariable Long comicId,
                            @PathVariable Long userId) {
        comicService.deleteComic(comicId, userId);
    }
}
