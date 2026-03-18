package com.mediatech.digitalpress.comment.client;

import com.mediatech.digitalpress.comment.dto.ArticleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "article-service")
public interface ArticleClient {

    @GetMapping("/articles/{id}")
    ArticleDTO getArticleById(@PathVariable Long id);
}
