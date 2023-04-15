package com.ITBlogs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="User")
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(
            name="id",
            unique = true
    )
    private Long id;
    @Column(
            name="username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String username;
    @Column(
            name="email",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String email;
    @Column(
            name="passwordHash",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String passwordHash;
    @Column(
            name="passwordSalt",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String passwordSalt;
    @Column(
            name="deleted",
            nullable = false
    )
    private Boolean deleted;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="user")
    private List<Article> articles = new ArrayList<>();
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy="likedArticles")
    private List<Article> likedArticles = new ArrayList<>();
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy="savedArticles")
    private List<Article> savedArticles = new ArrayList<>();
}
