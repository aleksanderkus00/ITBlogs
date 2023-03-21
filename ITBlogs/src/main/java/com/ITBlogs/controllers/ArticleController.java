package com.ITBlogs.controllers;

import com.ITBlogs.models.Article;
import com.ITBlogs.models.PaginatedResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @PostMapping()
    Article createArticle(@RequestBody Article article) {
        return null;
    }

    @GetMapping("/{id}")
    Article getArticleById(@PathVariable long id) {
        return null;
    }

    @PutMapping("/{id}")
    Article updateArticleById(@RequestBody Article article, @PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    void deleteArticleById(@PathVariable long id) {
    }

    @GetMapping("/all-articles")
    PaginatedResult<List<Article>> getAllArticles(){
        return null;
    }
}
