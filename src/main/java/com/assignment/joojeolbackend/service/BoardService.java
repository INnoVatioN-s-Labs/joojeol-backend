package com.assignment.joojeolbackend.service;

import com.assignment.joojeolbackend.domain.Comment;
import com.assignment.joojeolbackend.domain.Post;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    List<Post> getAllPosts();
    Post createPost(String content);
    List<Comment> getCommentsByPostId(UUID postId);
    Comment createComment(UUID postId, String content, String parentId);
    Post likePost(UUID postId);
    Post unlikePost(UUID postId);
}
