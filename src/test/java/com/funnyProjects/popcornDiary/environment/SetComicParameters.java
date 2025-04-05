/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.environment;

import com.funnyProjects.popcornDiary.model.Comic;
import com.funnyProjects.popcornDiary.model.User;

import java.time.LocalDate;

public class SetComicParameters {

    public static void setComicParameters(Comic comic, User user) {
        comic.setUser(user);
        comic.setTitle("Comic");
        comic.setChapters(24);
        comic.setFinishedDate(LocalDate.now());
        comic.setImagePath("/image/comic");
    }
}
