package com.goorm.board.taskboard.repository;

import com.goorm.board.taskboard.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findBySoftDeletedIsFalse(Sort sort);

    Optional<Post> findByIdAndSoftDeletedIsFalse(Long postId);
}
