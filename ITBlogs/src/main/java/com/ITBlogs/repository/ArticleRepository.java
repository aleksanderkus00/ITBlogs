package com.ITBlogs.repository;

import com.ITBlogs.models.Article;
import com.ITBlogs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
