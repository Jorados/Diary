package com.jorados.diary.controller;


import com.jorados.diary.domain.Comment;
import com.jorados.diary.exception.PostNotFound;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.request.comment.CommentCreate;
import com.jorados.diary.request.comment.CommentEdit;
import com.jorados.diary.response.CommentResponse;
import com.jorados.diary.service.CommentService;
import com.jorados.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    public void create(@RequestBody @Valid CommentCreate commentCreate){
        commentService.create(commentCreate);
    }

    @GetMapping("/comment/{commentId}")
    public CommentResponse read(@PathVariable Long commentId){
        return commentService.read(commentId);
    }

    //해당 post의 댓글 전부조회
    @GetMapping("/post/{postId}/comment")
    public List<Comment> readAll(@PathVariable Long postId){
        return commentService.readAll(postId);
    }

    @PatchMapping("/comment/{commentId}")
    public void update(@PathVariable Long commentId, CommentEdit commentEdit){
        commentService.update(commentId,commentEdit);
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
