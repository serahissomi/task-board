package com.goorm.board.taskboard.service;

import com.goorm.board.taskboard.domain.Post;
import com.goorm.board.taskboard.dto.CommentDto;
import com.goorm.board.taskboard.dto.PostDto;
import com.goorm.board.taskboard.exception.PostNotFoundException;
import com.goorm.board.taskboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDto> findAllPost() {
        Sort sort = Sort.by(Sort.Order.desc("updatedAt"));
        List<Post> posts = postRepository.findBySoftDeletedIsFalse(sort);

        if(posts.isEmpty()) {
            throw new PostNotFoundException("Post not found");
        }

        return posts.stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    public PostDto findPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findByIdAndSoftDeletedIsFalse(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            List<CommentDto> commentDtos = post.getComment().stream()
                    .map(comment -> CommentDto.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .build())
                    .collect(Collectors.toList());

            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .comments(commentDtos)
                    .build();

        } else {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }
    }

    @Transactional
    public Long createPost(PostDto postDto) {
        Post newPost = Post.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .build();
        return postRepository.save(newPost).getId();
    }

    @Transactional
    public void updatePost(Long postId, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findByIdAndSoftDeletedIsFalse(postId);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(postDto.getTitle());
            existingPost.setContent(postDto.getContent());

            postRepository.save(existingPost);
        } else {
            throw new PostNotFoundException("[Update] Post not found with id: " + postDto.getId());
        }
    }

    @Transactional
    public void softDeletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post deletedPost = optionalPost.get();
            deletedPost.setSoftDeleted(true);

            log.info(String.valueOf(deletedPost));

            postRepository.save(deletedPost);
        } else {
            throw new PostNotFoundException("[Delete] Post not found with id: " + postId);
        }
    }
}
