package com.assignment.joojeolbackend.service;

import com.assignment.joojeolbackend.domain.Comment;
import com.assignment.joojeolbackend.domain.Post;
import com.assignment.joojeolbackend.repository.CommentRepository;
import com.assignment.joojeolbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Post createPost(String content) {
        // Mock image/hashtags for MVP simplicity or extend later
        return postRepository.save(Post.builder()
                .content(content)
                .build());
    }

    public List<Comment> getCommentsByPostId(UUID postId) {
        return commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtAsc(postId);
    }

    @Transactional
    public Comment createComment(UUID postId, String content, String parentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment parent = null;
        if (parentId != null) {
            parent = commentRepository.findById(UUID.fromString(parentId))
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
        }

        Comment comment = Comment.builder()
                .content(content)
                .author("익명") // Fixed author for MVP
                .isMe(true)   // In real app, check session
                .post(post)
                .parent(parent)
                .build();
        
        return commentRepository.save(comment);
    }

    @Transactional
    public Post likePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (post.isReacted()) {
            return post;
        }

        post.addLike();
        post.setReacted(true);
        return postRepository.save(post);
    }

    @Transactional
    public Post unlikePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.isReacted()) {
            return post;
        }

        post.removeLike();
        post.setReacted(false);
        return postRepository.save(post);
    }
}
