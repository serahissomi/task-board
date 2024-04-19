package com.goorm.board.taskboard.repository;

import com.goorm.board.taskboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
