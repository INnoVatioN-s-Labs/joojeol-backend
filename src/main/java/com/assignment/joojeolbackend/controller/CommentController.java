package com.assignment.joojeolbackend.controller;

import com.assignment.joojeolbackend.domain.Comment;
import com.assignment.joojeolbackend.dto.comment.CommentCreateReq;
import com.assignment.joojeolbackend.dto.comment.CommentRes;
import com.assignment.joojeolbackend.dto.common.ReturnMessage;
import com.assignment.joojeolbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;

    @GetMapping
    public ReturnMessage<List<CommentRes>> getComments(@PathVariable UUID postId) {
        List<Comment> comments = postService.getCommentsByPostId(postId);
        List<CommentRes> commentResList = comments.stream()
                .map(CommentRes::new)
                .collect(Collectors.toList());
        return new ReturnMessage<>(commentResList);
    }

    @PostMapping
    public ReturnMessage<CommentRes> createComment(
            @PathVariable UUID postId,
            @RequestBody CommentCreateReq req
    ) {
        Comment comment = postService.createComment(postId, req.getContent(), req.getParentId());
        return new ReturnMessage<>(new CommentRes(comment));
    }
}
