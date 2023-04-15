package com.ITBlogs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Photo")
@Table(name="photos")
public class Photo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(
            name="id",
            unique = true
    )
    private Long id;

    @Column(
            name="bytes",
            nullable = false,
            columnDefinition = "BYTEA"
    )
    private byte[] bytes = new byte[0];
    @Column(
            name="description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Column(
            name="size",
            nullable = false
    )
    private Long size;
    @Column(
            name="deleted",
            nullable = false
    )
    private Boolean deleted;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(
            name="article_id",
            referencedColumnName="id",
            nullable = false
    )
    private Article article;
}
