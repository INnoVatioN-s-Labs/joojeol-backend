package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.domain.Post;
import com.assignment.joojeolbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final BoardService boardService;

    @GetMapping
    public List<Post> getPosts() {
        return boardService.getAllPosts();
    }

    @PostMapping
    public Post createPost(@RequestBody Map<String, String> body) {
        return boardService.createPost(body.get("content"));
    }
}
