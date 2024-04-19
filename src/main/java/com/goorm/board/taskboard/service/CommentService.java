package com.goorm.board.taskboard.service;

import com.goorm.board.taskboard.domain.Comment;
import com.goorm.board.taskboard.domain.Post;
import com.goorm.board.taskboard.dto.CommentDto;
import com.goorm.board.taskboard.repository.CommentRepository;
import com.goorm.board.taskboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long createComment(Long id, CommentDto commentDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + id));
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .post(post)
                .build();

        return commentRepository.save(comment).getId();
    }
}
