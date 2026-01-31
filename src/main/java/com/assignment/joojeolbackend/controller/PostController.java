package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.domain.Post;
import com.assignment.joojeolbackend.dto.common.ReturnMessage;
import com.assignment.joojeolbackend.dto.post.PostCreateReq;
import com.assignment.joojeolbackend.dto.post.PostRes;
import com.assignment.joojeolbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    public ReturnMessage<List<PostRes>> getPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostRes> postResList = posts.stream()
                .map(PostRes::new)
                .collect(Collectors.toList());
        return new ReturnMessage<>(postResList);
    }

    @GetMapping("/search")
    public ReturnMessage<List<PostRes>> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> posts = postService.searchPosts(keyword);
        List<PostRes> postResList = posts.stream()
                .map(PostRes::new)
                .collect(Collectors.toList());
        return new ReturnMessage<>(postResList);
    }

    @PostMapping
    public ReturnMessage<PostRes> createPost(@RequestBody PostCreateReq req) {
        Post post = postService.createPost(req.getContent());
        return new ReturnMessage<>(new PostRes(post));
    }

    @PostMapping("/{postId}/like")
    public ReturnMessage<Void> likePost(@PathVariable UUID postId) {
        postService.likePost(postId);
        return new ReturnMessage<>(null);
    }

    @PostMapping("/{postId}/unlike")
    public ReturnMessage<Void> unlikePost(@PathVariable UUID postId) {
        postService.unlikePost(postId);
        return new ReturnMessage<>(null);
    }
}
