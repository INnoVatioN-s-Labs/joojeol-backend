package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.domain.Comment;
import com.assignment.joojeolbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final BoardService boardService;

    @GetMapping
    public List<Comment> getComments(@PathVariable UUID postId) {
        return boardService.getCommentsByPostId(postId);
    }

    @PostMapping
    public Comment createComment(
            @PathVariable UUID postId,
            @RequestBody Map<String, String> body
    ) {
        return boardService.createComment(postId, body.get("content"), body.get("parentId"));
    }
}
