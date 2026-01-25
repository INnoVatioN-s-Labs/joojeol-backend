package com.assignment.joojeolbackend.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "게시글 작성 요청")
public class PostCreateReq {
    @Schema(description = "게시글 내용", example = "오늘 하루 어땠나요?")
    private String content;
}
