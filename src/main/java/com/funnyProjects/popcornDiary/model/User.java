/*
 * Created by minmin_tranova on 03.04.2025
 */

package com.funnyProjects.popcornDiary.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<Movie> movies;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<Comic> comics;

}
