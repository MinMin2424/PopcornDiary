/*
 * Created by minmin_tranova on 05.04.2025
 */

package com.funnyProjects.popcornDiary.environment;

import com.funnyProjects.popcornDiary.model.User;

public class SetUserParameters {

    public static void setUserParameters(User user, String email) {
        user.setEmail(email);
        user.setPassword("password");
    }

}
