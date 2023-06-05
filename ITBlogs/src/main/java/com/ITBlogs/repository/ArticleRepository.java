package com.ITBlogs.repository;

import com.ITBlogs.models.Article;
import com.ITBlogs.models.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByCategory(Long category, PageRequest pagination);
    List<Article> findAllByCategory(Long category);
    List<Article> findAllByUser(User user, PageRequest pagination);
    List<Article> findAllByUser(User user);
}
