package com.ITBlogs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Article")
@Table(name="articles")
public class Article {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(
            name="id",
            unique = true
    )
    private Long id;
    @Column(
            name="title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;
    @Column(
            name="content",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;
    @Column(
            name="deleted",
            nullable = false
    )
    private Boolean deleted;

    @Column(
            name="category"
    )
    private Long category;
    @Column(
            name="generatedDate",
            nullable = false
    )
    private LocalDateTime generatedDate;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(
            name="user_id",
            referencedColumnName="id",
            nullable = false
    )
    private User user;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy="article")
    private List<Photo> articles = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="likedArticles",
            joinColumns = @JoinColumn(name="user_id"), // inverse those ids
            inverseJoinColumns = @JoinColumn(name="article_id")
    )
    private List<User> likedArticles = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="savedArticles",
            joinColumns = @JoinColumn(name="user_id"), // inverse those ids
            inverseJoinColumns = @JoinColumn(name="article_id")
    )
    private List<User> savedArticles = new ArrayList<>();

    public void likedBy(User user){
        this.likedArticles.add(user);
    }

    public void unlikedBy(User user){
        this.likedArticles.remove(user);
    }

    public void savedBy(User user) {
        this.savedArticles.add(user);
    }

    public void unsavedBy(User user) {
        this.savedArticles.remove(user);
    }
}
