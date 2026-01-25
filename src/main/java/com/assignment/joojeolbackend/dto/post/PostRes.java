package com.assignment.joojeolbackend.dto.post;

import com.assignment.joojeolbackend.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "게시글 응답")
public class PostRes {
    @Schema(description = "게시글 ID")
    private UUID id;

    @Schema(description = "게시글 내용")
    private String content;

    @Schema(description = "반응(공감) 수")
    private int reactionCount;

    @Schema(description = "이미지 URL")
    private String imageUrl;

    @Schema(description = "해시태그 목록")
    private List<String> hashtags;

    @Schema(description = "작성일시")
    private LocalDateTime createdAt;

    @Schema(description = "현재 사용자의 반응 여부")
    private boolean isReacted;

    public PostRes(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.reactionCount = post.getLikeCount();
        this.imageUrl = post.getImageUrl();
        this.hashtags = post.getHashtags();
        this.createdAt = post.getCreatedAt();
        this.isReacted = post.isReacted();
    }
}
