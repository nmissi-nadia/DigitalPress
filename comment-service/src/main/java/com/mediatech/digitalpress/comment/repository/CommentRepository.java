package com.mediatech.digitalpress.comment.repository;

import com.mediatech.digitalpress.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByUserId(Long userId);
    List<Comment> findByStatus(Comment.CommentStatus status);
}
