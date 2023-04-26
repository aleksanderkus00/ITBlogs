package com.ITBlogs.controllers;

import com.ITBlogs.models.Article;
import com.ITBlogs.models.DTO.ArticleDto;
import com.ITBlogs.repository.ArticleRepository;
import com.ITBlogs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    boolean createArticle(@RequestBody Article article, @PathVariable long userId) {
        try {
            var user = this.userRepository.findById(userId).get();
            article.setUser(user);
            article.setGeneratedDate(LocalDateTime.now());
            article.setDeleted(false);
            article.setCategory(1L); //test only
            this.articleRepository.save(article);
            return true;
        } catch (Exception exception) {
            this.logger.error(exception.getMessage());
        }
        return false;
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
    List<ArticleDto> getAllArticles(@PathVariable int pageNumber, @PathVariable int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var articles =  this.articleRepository.findAll(pageable).getContent();
        var articleDtos = new ArrayList<ArticleDto>(articles.size());
        for(var article: articles){
            var articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setTitle(article.getTitle());
            articleDto.setCategory(article.getCategory());
            articleDto.setContent(article.getContent());
            articleDto.setGeneratedDate(article.getGeneratedDate());
            articleDto.setLikes((long) article.getLikedArticles().size());
            articleDtos.add(articleDto);
        }
        return articleDtos;
    }

    @GetMapping("/liked/{userId}/{pageNumber}/{pageSize}")
    List<Article> getLikedArticles(@PathVariable long userId, @PathVariable int pageNumber, @PathVariable int pageSize) {
        try{
            var user = this.userRepository.findById(userId).get();
            return user.getLikedArticles();
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get saved articles");
        }
        return null;
    }

    @PutMapping("/like/{userId}/{articleId}")
    boolean likeArticles(@PathVariable long userId, @PathVariable long articleId) {
        try {
            var user = this.userRepository.findById(userId).get();
            var article = this.articleRepository.findById(articleId).get();
            article.likedBy(user);
            this.articleRepository.save(article);
            return true;
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot like article with id: " + articleId + " for user with id: " + userId);
        }
        return false;
    }

    @GetMapping("/saved/{userId}/{pageNumber}/{pageSize}")
    List<Article> getSavedArticles(@PathVariable long userId, @PathVariable int pageNumber, @PathVariable int pageSize) {
        try{
            var user = this.userRepository.findById(userId).get();
            return user.getSavedArticles();
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get saved articles");
        }
        return null;
    }

    @PutMapping("/save/{userId}/{articleId}")
    boolean saveArticles(@PathVariable long userId, @PathVariable long articleId) {
        try {
            var user = this.userRepository.findById(userId).get();
            var article = this.articleRepository.findById(articleId).get();
            article.savedBy(user);
            this.articleRepository.save(article);
            return true;
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot like article with id: " + articleId + " for user with id: " + userId);
        }
        return false;
    }

}
