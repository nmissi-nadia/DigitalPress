package com.mediatech.digitalpress.article.service;

import com.mediatech.digitalpress.article.client.UserClient;
import com.mediatech.digitalpress.article.dto.ArticleDTO;
import com.mediatech.digitalpress.article.dto.CategoryDTO;
import com.mediatech.digitalpress.article.dto.TagDTO;
import com.mediatech.digitalpress.article.dto.UserDTO;
import com.mediatech.digitalpress.article.entity.Article;
import com.mediatech.digitalpress.article.entity.ArticleStatus;
import com.mediatech.digitalpress.article.repository.ArticleRepository;
import com.mediatech.digitalpress.article.repository.CategoryRepository;
import com.mediatech.digitalpress.article.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserClient userClient;

    public ArticleDTO saveArticle(Article article) {
        article.setCreatedDate(LocalDateTime.now());
        if (article.getStatus() == ArticleStatus.PUBLISHED) {
            article.setPublishedDate(LocalDateTime.now());
        }
        Article saved = articleRepository.save(article);
        return convertToDTO(saved);
    }

    public Optional<ArticleDTO> findById(Long id) {
        return articleRepository.findById(id).map(this::convertToDTO);
    }

    public List<ArticleDTO> findAll() {
        return articleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ArticleDTO> findByAuthorId(Long authorId) {
        return articleRepository.findByAuthorId(authorId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ArticleDTO> findPublished() {
        return articleRepository.findByStatus(ArticleStatus.PUBLISHED).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    private ArticleDTO convertToDTO(Article article) {
        UserDTO author = userClient.getUserById(article.getAuthorId());
        Set<CategoryDTO> categories = article.getCategories().stream()
                .map(cat -> new CategoryDTO(cat.getId(), cat.getName()))
                .collect(Collectors.toSet());
        Set<TagDTO> tags = article.getTags().stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toSet());
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent(),
                author, article.getStatus().name(), article.getCreatedDate(),
                article.getPublishedDate(), categories, tags);
    }
}
