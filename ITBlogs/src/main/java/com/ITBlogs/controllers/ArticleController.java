package com.ITBlogs.controllers;

import com.ITBlogs.models.Article;
import com.ITBlogs.models.PaginatedResult;
import com.ITBlogs.repository.ArticleRepository;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}")
    Article createArticle(@RequestBody Article article, @PathVariable long userId) {
        try {
            var user = this.userRepository.findById(userId).get();
            article.setUser(user);
            article.setGeneratedDate(LocalDateTime.now());
            return this.articleRepository.save(article);
        } catch (Exception exception) {
            this.logger.error(exception.getMessage());
        }
        return null;
    }

    @GetMapping("/{id}")
    Article getArticleById(@PathVariable long id) {
        try{
            return this.articleRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get user with id: " + id);
        }
        return null;
    }

    @PutMapping("/{id}")
    Article updateArticleById(@RequestBody Article updatedArticle, @PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    void deleteArticleById(@PathVariable long id) {
        try{
            var article = this.articleRepository.findById(id).get();
            article.setDeleted(true);
            this.articleRepository.save(article);
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot delete user with id: " + id);
        }
    }

    @GetMapping("/all-articles/{pageNumber}/{pageSize}")
    //PaginatedResult<List<Article>> getAllArticles(
    List<Article> getAllArticles(
            @PathVariable long pageNumber,
            @PathVariable long pageSize)
    {
        var data =  this.articleRepository.findAll()
                .stream()
                .skip(pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        return data;
    }
}
