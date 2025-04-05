package com.funnyProjects.popcornDiary;

import com.funnyProjects.popcornDiary.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class PopcornDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopcornDiaryApplication.class, args);
	}

}
