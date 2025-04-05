/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.environment;

import com.funnyProjects.popcornDiary.model.Movie;
import com.funnyProjects.popcornDiary.model.User;
import com.funnyProjects.popcornDiary.model.enums.MovieCountry;

public class SetMovieParameters {

    public static void setMovieParameters(Movie movie, User user) {
        movie.setUser(user);
        movie.setTitle("Movie");
        movie.setCountry("Korea");
        movie.setEpisodes(24);
        movie.setYear(2025);
        movie.setImagePath("/images/movie");
    }

    public static void setMovieParameters(Movie movie, User user, Integer year) {
        movie.setUser(user);
        movie.setTitle("Movie");
        movie.setCountry("Korea");
        movie.setEpisodes(24);
        movie.setYear(year);
        movie.setImagePath("/images/movie");
    }

    public static void setMovieParameters(Movie movie, User user, String country) {
        movie.setUser(user);
        movie.setTitle("Movie");
        movie.setCountry(country);
        movie.setEpisodes(24);
        movie.setYear(2025);
        movie.setImagePath("/images/movie");
    }

}
