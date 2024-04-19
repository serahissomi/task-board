package com.goorm.board.taskboard.controller;

import com.goorm.board.taskboard.dto.ApiResponse;
import com.goorm.board.taskboard.dto.CommentDto;
import com.goorm.board.taskboard.dto.PostDto;
import com.goorm.board.taskboard.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PatchMapping("/{postId}")
    public ResponseEntity<?> softDeleteComment(@PathVariable long postId) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/{postId}")
    public ResponseEntity<?> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto) {
        Long commentId = commentService.createComment(postId, commentDto);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), "Create Comment - postId : " + postId + " commentId : " + commentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<?> updateComment(@PathVariable long postId, @RequestBody PostDto updatedPostDto) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{postId}")
    public ResponseEntity<?> updateComment(@PathVariable long postId, @RequestBody Map<String, Object> updatedFields) {

        return ResponseEntity.ok().build();
    }
}
