package com.goorm.board.taskboard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;

    @NotNull(message = "내용이 비어있음")
    private String content;
}
