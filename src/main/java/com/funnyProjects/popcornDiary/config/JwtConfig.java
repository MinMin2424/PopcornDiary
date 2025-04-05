/*
 * Created by minmin_tranova on 04.04.2025
 */

package com.funnyProjects.popcornDiary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtConfig {

    private String secret;
    private long expiration;

}
