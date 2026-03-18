package com.mediatech.digitalpress.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long articleId;
    private Long userId;
    @Column(length = 1000)
    private String content;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    private LocalDateTime createdDate;
}
