package com.mediatech.digitalpress.article.controller;

import com.mediatech.digitalpress.article.dto.ArticleDTO;
import com.mediatech.digitalpress.article.entity.Article;
import com.mediatech.digitalpress.article.entity.ArticleStatus;
import com.mediatech.digitalpress.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EventProducer eventProducer;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        ArticleDTO saved = articleService.saveArticle(article);
        if (article.getStatus() == ArticleStatus.PUBLISHED) {
            eventProducer.sendArticlePublishedEvent(saved.getId(), article.getAuthorId());
        }
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        return articleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.findAll();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/published")
    public ResponseEntity<List<ArticleDTO>> getPublishedArticles() {
        List<ArticleDTO> articles = articleService.findPublished();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByAuthor(@PathVariable Long authorId) {
        List<ArticleDTO> articles = articleService.findByAuthorId(authorId);
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        return articleService.findById(id).map(existing -> {
            existing.setTitle(article.getTitle());
            existing.setContent(article.getContent());
            existing.setStatus(article.getStatus());
            existing.setCategories(article.getCategories());
            existing.setTags(article.getTags());
            if (article.getStatus() == ArticleStatus.PUBLISHED && existing.getPublishedDate() == null) {
                existing.setPublishedDate(java.time.LocalDateTime.now());
            }
            ArticleDTO updated = articleService.saveArticle(existing);
            if (article.getStatus() == ArticleStatus.PUBLISHED && existing.getStatus() != ArticleStatus.PUBLISHED) {
                eventProducer.sendArticlePublishedEvent(updated.getId(), existing.getAuthorId());
            }
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
