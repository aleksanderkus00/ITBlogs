package com.ITBlogs.models.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private Long category;
    private LocalDateTime generatedDate;
    private Long likes;
}
