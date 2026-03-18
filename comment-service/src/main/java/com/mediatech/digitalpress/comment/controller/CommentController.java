package com.mediatech.digitalpress.comment.controller;

import com.mediatech.digitalpress.comment.dto.CommentDTO;
import com.mediatech.digitalpress.comment.entity.Comment;
import com.mediatech.digitalpress.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment) {
        CommentDTO saved = commentService.saveComment(comment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentDTO> comments = commentService.findByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CommentDTO>> getPendingComments() {
        List<CommentDTO> comments = commentService.findAllPending();
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approveComment(@PathVariable Long id) {
        commentService.approveComment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> rejectComment(@PathVariable Long id) {
        commentService.rejectComment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
