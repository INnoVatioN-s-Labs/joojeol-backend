package com.assignment.joojeolbackend.dto.comment;

import com.assignment.joojeolbackend.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Schema(description = "댓글 응답")
public class CommentRes {
    @Schema(description = "댓글 ID")
    private UUID id;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "작성자 닉네임")
    private String author;

    @Schema(description = "내 댓글 여부")
    private boolean isMe;

    @Schema(description = "작성일시")
    private LocalDateTime createdAt;

    @Schema(description = "대댓글 목록")
    private List<CommentRes> children;

    public CommentRes(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.isMe = comment.isMe();
        this.createdAt = comment.getCreatedAt();
        // Assuming children are fetched or standard empty list
        // Caution: Infinite recursion potential if children have parents referencing back.
        // CommentRes does not have 'parent' field, so it's safe downwards.
        if (comment.getChildren() != null) {
            this.children = comment.getChildren().stream()
                    .map(CommentRes::new)
                    .collect(Collectors.toList());
        }
    }
}
