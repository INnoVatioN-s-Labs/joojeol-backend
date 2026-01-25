package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.domain.Post;
import com.assignment.joojeolbackend.dto.common.ReturnMessage;
import com.assignment.joojeolbackend.dto.post.PostCreateReq;
import com.assignment.joojeolbackend.dto.post.PostRes;
import com.assignment.joojeolbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final BoardService boardService;

    @GetMapping
    public ReturnMessage<List<PostRes>> getPosts() {
        List<Post> posts = boardService.getAllPosts();
        List<PostRes> postResList = posts.stream()
                .map(PostRes::new)
                .collect(Collectors.toList());
        return new ReturnMessage<>(postResList);
    }

    @PostMapping
    public ReturnMessage<PostRes> createPost(@RequestBody PostCreateReq req) {
        Post post = boardService.createPost(req.getContent());
        return new ReturnMessage<>(new PostRes(post));
    }

    @PostMapping("/{postId}/like")
    public ReturnMessage<PostRes> likePost(@PathVariable UUID postId) {
        Post post = boardService.likePost(postId);
        return new ReturnMessage<>(new PostRes(post));
    }

    @PostMapping("/{postId}/unlike")
    public ReturnMessage<PostRes> unlikePost(@PathVariable UUID postId) {
        Post post = boardService.unlikePost(postId);
        return new ReturnMessage<>(new PostRes(post));
    }
}
