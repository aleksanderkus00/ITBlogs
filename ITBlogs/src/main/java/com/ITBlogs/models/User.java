package com.ITBlogs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


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
            columnDefinition = "BYTEA"
    )
    private byte[] passwordHash;
    @Column(
            name="passwordSalt",
            nullable = false,
            columnDefinition = "BYTEA"
    )
    private byte[] passwordSalt;
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

    public User(String username, String email, String password) throws NoSuchAlgorithmException {
        this.id = 0L;
        this.username = username;
        this.email = email;
        this.deleted = false;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        byte[] saltedPassword = (password + new String(salt)).getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        this.passwordHash  = md.digest(saltedPassword);
        this.passwordSalt = salt;
    }
}
