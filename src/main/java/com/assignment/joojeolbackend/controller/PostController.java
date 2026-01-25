package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.domain.Post;
import com.assignment.joojeolbackend.dto.common.ReturnMessage;
import com.assignment.joojeolbackend.dto.post.PostCreateReq;
import com.assignment.joojeolbackend.dto.post.PostRes;
import com.assignment.joojeolbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/search")
    public ReturnMessage<List<PostRes>> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> posts = boardService.searchPosts(keyword);
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
    public ReturnMessage<Void> likePost(@PathVariable UUID postId) {
        boardService.likePost(postId);
        return new ReturnMessage<>(null);
    }

    @PostMapping("/{postId}/unlike")
    public ReturnMessage<Void> unlikePost(@PathVariable UUID postId) {
        boardService.unlikePost(postId);
        return new ReturnMessage<>(null);
    }
}
