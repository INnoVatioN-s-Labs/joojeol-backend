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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "hashtag")
    private List<String> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.Set<PostLike> postLikeList = new java.util.LinkedHashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "reaction_count", nullable = false)
    private int reactionCount = 0;

    @Column(nullable = false)
    private boolean isReacted = false; // Mocking per-user reaction for now (simpler MVP)

    @Builder
    public Post(String content, String imageUrl, List<String> hashtags) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.hashtags = hashtags != null ? hashtags : new ArrayList<>();
    }

    public int getLikeCount() {
        return reactionCount;
    }

    public void setReacted(boolean reacted) {
        this.isReacted = reacted;
    }


    public void addLike() {
        boolean added = this.postLikeList.add(PostLike.create(this));
        if (added) {
            reactionCount++;
        }
    }

    public void removeLike() {
        if (!postLikeList.isEmpty()) {
            java.util.Iterator<PostLike> it = postLikeList.iterator();
            PostLike last = null;
            while (it.hasNext()) {
                last = it.next();
            }
            if (last != null && postLikeList.remove(last)) {
                reactionCount = Math.max(0, reactionCount - 1);
            }
        }
    }

}
