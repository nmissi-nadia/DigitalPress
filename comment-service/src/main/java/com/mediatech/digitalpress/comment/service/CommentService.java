package com.mediatech.digitalpress.comment.service;

import com.mediatech.digitalpress.comment.client.ArticleClient;
import com.mediatech.digitalpress.comment.client.UserClient;
import com.mediatech.digitalpress.comment.dto.CommentDTO;
import com.mediatech.digitalpress.comment.entity.Comment;
import com.mediatech.digitalpress.comment.entity.CommentStatus;
import com.mediatech.digitalpress.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleClient articleClient;

    @Autowired
    private UserClient userClient;

    public CommentDTO saveComment(Comment comment) {
        comment.setCreatedDate(LocalDateTime.now());
        comment.setStatus(CommentStatus.PENDING); // default status
        Comment saved = commentRepository.save(comment);
        return convertToDTO(saved);
    }

    public Optional<CommentDTO> findById(Long id) {
        return commentRepository.findById(id).map(this::convertToDTO);
    }

    public List<CommentDTO> findByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId).stream()
                .filter(c -> c.getStatus() == CommentStatus.APPROVED)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> findAllPending() {
        return commentRepository.findByStatus(CommentStatus.PENDING).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void approveComment(Long id) {
        commentRepository.findById(id).ifPresent(comment -> {
            comment.setStatus(CommentStatus.APPROVED);
            commentRepository.save(comment);
        });
    }

    public void rejectComment(Long id) {
        commentRepository.findById(id).ifPresent(comment -> {
            comment.setStatus(CommentStatus.REJECTED);
            commentRepository.save(comment);
        });
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO convertToDTO(Comment comment) {
        ArticleDTO article = articleClient.getArticleById(comment.getArticleId());
        UserDTO user = userClient.getUserById(comment.getUserId());
        return new CommentDTO(comment.getId(), article, user, comment.getContent(),
                comment.getStatus().name(), comment.getCreatedDate());
    }
}
