package com.assignment.joojeolbackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_liked")
public class PostLiked {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(nullable = false)
    private int likeCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public PostLiked(Post post, Integer likeCount) {
        this.post = post;
        this.likeCount = likeCount != null ? likeCount : 0;
    }

    public static PostLiked create(Post post) {
        return PostLiked.builder()
                .post(post)
                .build();
    }

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
