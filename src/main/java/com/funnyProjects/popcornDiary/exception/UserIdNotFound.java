package com.funnyProjects.popcornDiary.exception;

public class UserIdNotFound extends RuntimeException {
    public UserIdNotFound(String message) {
        super(message);
    }
}
