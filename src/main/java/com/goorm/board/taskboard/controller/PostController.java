package com.goorm.board.taskboard.controller;

import com.goorm.board.taskboard.dto.ApiResponse;
import com.goorm.board.taskboard.dto.PostDto;
import com.goorm.board.taskboard.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<?> showAllPost() {
        List<PostDto> posts = postService.findAllPost();

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> showPost(@PathVariable Long postId) {
        PostDto postById = postService.findPostById(postId);

        return ResponseEntity.status(HttpStatus.OK).body(postById);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> softDeletePost(@PathVariable Long postId) {
        postService.softDeletePost(postId);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.NO_CONTENT.value(), "Post deleted with id: " + postId);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDto postRequest) {
        Long postId = postService.createPost(postRequest);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), "Post created with id: " + postId);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostDto updatedPostDto) {
        postService.updatePost(postId, updatedPostDto);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), "Post updated with id: " + postId);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PatchMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Map<String, Object> updatedFields) {

        return ResponseEntity.ok().build();
    }
}
