package com.mediatech.digitalpress.article.repository;

import com.mediatech.digitalpress.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthorId(Long authorId);
    List<Article> findByStatus(Article.ArticleStatus status);
}
