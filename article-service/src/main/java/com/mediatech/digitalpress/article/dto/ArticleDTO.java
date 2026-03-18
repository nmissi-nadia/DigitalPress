package com.mediatech.digitalpress.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Set<CategoryDTO> categories;
    private Set<TagDTO> tags;
}
