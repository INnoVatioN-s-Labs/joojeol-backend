package com.assignment.joojeolbackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "hashtag")
    private List<String> hashtags = new ArrayList<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PostLiked postLiked;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private boolean isReacted = false; // Mocking per-user reaction for now (simpler MVP)

    @Builder
    public Post(String content, String imageUrl, List<String> hashtags) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.hashtags = hashtags != null ? hashtags : new ArrayList<>();
        this.postLiked = PostLiked.create(this);
    }

    public int getLikeCount() {
        return postLiked != null ? postLiked.getLikeCount() : 0;
    }

    public PostLiked ensurePostLiked() {
        if (postLiked == null) {
            this.postLiked = PostLiked.create(this);
        }
        return postLiked;
    }

    public void setReacted(boolean reacted) {
        this.isReacted = reacted;
    }
}
