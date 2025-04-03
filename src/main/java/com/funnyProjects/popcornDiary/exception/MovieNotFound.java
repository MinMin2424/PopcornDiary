package com.funnyProjects.popcornDiary.exception;

public class MovieNotFound extends RuntimeException {
    public MovieNotFound(String message) {
        super(message);
    }
}
