package com.ITBlogs.controllers;

import com.ITBlogs.models.Article;
import com.ITBlogs.repository.ArticleRepository;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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
    List<Article> getAllArticles(@PathVariable int pageNumber,@PathVariable int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return this.articleRepository.findAll(pageable).getContent();
    }

    @GetMapping("/liked/{userId}/{pageNumber}/{pageSize}")
    List<Article> getLikedArticles(@PathVariable int userId, @PathVariable int pageNumber, @PathVariable int pageSize) {
        return null;
    }

    @PostMapping("/like/{userId}/{articleId}")
    boolean likeArticles(@PathVariable int userId, @PathVariable int articleId) {
        return true;
    }

    @GetMapping("/saved/{userId}/{pageNumber}/{pageSize}")
    List<Article> getSavedArticles(@PathVariable int userId, @PathVariable int pageNumber, @PathVariable int pageSize) {
        return null;
    }

    @PostMapping("/save/{userId}/{articleId}")
    boolean saveArticles(@PathVariable int userId, @PathVariable int articleId) {
        return true;
    }

}
