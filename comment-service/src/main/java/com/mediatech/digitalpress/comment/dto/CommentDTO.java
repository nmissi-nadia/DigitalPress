package com.mediatech.digitalpress.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private ArticleDTO article;
    private UserDTO user;
    private String content;
    private String status;
    private LocalDateTime createdDate;
}
