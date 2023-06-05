package com.ITBlogs.controllers;

import com.ITBlogs.models.Article;
import com.ITBlogs.models.DTO.ArticleDto;
import com.ITBlogs.models.PaginatedResult;
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
    boolean createArticle(@RequestBody Article article, @PathVariable long userId) {
        try {
            var user = this.userRepository.findById(userId).get();
            article.setUser(user);
            article.setGeneratedDate(LocalDateTime.now());
            article.setDeleted(false);
            article.setCategory(article.getCategory());
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
    PaginatedResult<List<ArticleDto>> getAllArticles(@PathVariable int pageNumber, @PathVariable int pageSize) {
        int totalCount = this.articleRepository.findAll().size() / pageSize;
        var pageable = PageRequest.of(pageNumber, pageSize);
        var articles =  this.articleRepository.findAll(pageable).getContent();
        var articleDtos = new ArrayList<ArticleDto>(articles.size());
        for(var article: articles){
            var articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setTitle(article.getTitle());
            articleDto.setCategory(article.getCategory());
            int endIndex = Math.min(article.getContent().length(), 100);
            articleDto.setContent(article.getContent().substring(0, endIndex));
            articleDto.setGeneratedDate(article.getGeneratedDate());
            articleDto.setLikes((long) article.getLikedArticles().size());
            articleDtos.add(articleDto);
        }
        var paginatedResult = new PaginatedResult<List<ArticleDto>>();
        paginatedResult.setResult(articleDtos);
        paginatedResult.setCurrentPage(pageNumber);
        paginatedResult.setTotalPages(totalCount);
        return paginatedResult;
    }

    @GetMapping("your-articles/{userId}/{pageNumber}/{pageSize}")
        //PaginatedResult<List<Article>> getAllArticles(
    List<ArticleDto> getYourArticles(@PathVariable Long userId, @PathVariable int pageNumber, @PathVariable int pageSize) {
        try {
            var user = this.userRepository.findById(userId).get();
            var pageable = PageRequest.of(pageNumber, pageSize);
            var articles =  this.articleRepository.findAllByUser(user, pageable);
            var articleDtos = new ArrayList<ArticleDto>(articles.size());
            for(var article: articles){
                var articleDto = new ArticleDto();
                articleDto.setId(article.getId());
                articleDto.setTitle(article.getTitle());
                articleDto.setCategory(article.getCategory());
                articleDto.setContent(article.getContent()); // TODO: take only first 100 characters
                articleDto.setGeneratedDate(article.getGeneratedDate());
                articleDto.setLikes((long) article.getLikedArticles().size());
                articleDtos.add(articleDto);
            }
            return articleDtos;
        }
        catch (Exception ex){
            this.logger.warn("Cannot get news articles");
        }
        return new ArrayList<>();
    }

    @GetMapping("/news-articles/{pageNumber}/{pageSize}")
        //PaginatedResult<List<Article>> getAllArticles(
    List<ArticleDto> getNewArticles(@PathVariable int pageNumber, @PathVariable int pageSize) {
        try {
            var pageable = PageRequest.of(pageNumber, pageSize);
            var articles =  this.articleRepository.findAllByCategory(0L,pageable);
            var articleDtos = new ArrayList<ArticleDto>(articles.size());
            for(var article: articles){
                var articleDto = new ArticleDto();
                articleDto.setId(article.getId());
                articleDto.setTitle(article.getTitle());
                articleDto.setCategory(article.getCategory());
                articleDto.setContent(article.getContent()); // TODO: take only first 100 characters
                articleDto.setGeneratedDate(article.getGeneratedDate());
                articleDto.setLikes((long) article.getLikedArticles().size());
                articleDtos.add(articleDto);
            }
            return articleDtos;
        }
        catch (Exception ex){
            this.logger.warn("Cannot get news articles");
        }
        return new ArrayList<>();
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

    @GetMapping("/userLikes/{userId}")
    List<Long> getUserLikes(@PathVariable long userId) {
        try{
            var user = this.userRepository.findById(userId).get();
            return user.getLikedArticles().stream().map(Article::getId).collect(Collectors.toList());
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get saved articles");
        }
        return new ArrayList<>();
    }

    @GetMapping("/userSaves/{userId}")
    List<Long> getUserSaves(@PathVariable long userId) {
        try{
            var user = this.userRepository.findById(userId).get();
            return user.getSavedArticles().stream().map(Article::getId).collect(Collectors.toList());
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot get saved articles");
        }
        return new ArrayList<>();
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

    @PutMapping("/unlike/{userId}/{articleId}")
    boolean unlikeArticles(@PathVariable long userId, @PathVariable long articleId) {
        try {
            var user = this.userRepository.findById(userId).get();
            var article = this.articleRepository.findById(articleId).get();
            article.unlikedBy(user);
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

    @PutMapping("/unsave/{userId}/{articleId}")
    boolean unSaveArticles(@PathVariable long userId, @PathVariable long articleId) {
        try {
            var user = this.userRepository.findById(userId).get();
            var article = this.articleRepository.findById(articleId).get();
            article.unsavedBy(user);
            this.articleRepository.save(article);
            return true;
        } catch (NoSuchElementException exception) {
            this.logger.warn("Cannot like article with id: " + articleId + " for user with id: " + userId);
        }
        return false;
    }
}
