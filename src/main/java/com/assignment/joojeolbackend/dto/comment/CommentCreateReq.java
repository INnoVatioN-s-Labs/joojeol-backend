package com.assignment.joojeolbackend.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "댓글 작성 요청")
public class CommentCreateReq {
    @Schema(description = "댓글 내용", example = "저도 그렇게 생각해요!")
    private String content;

    @Schema(description = "부모 댓글 ID (대댓글인 경우)", nullable = true)
    private String parentId; // Optional, can be null
}
