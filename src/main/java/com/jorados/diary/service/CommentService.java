package com.jorados.diary.service;


import com.jorados.diary.domain.Comment;
import com.jorados.diary.exception.PostNotFound;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.request.comment.CommentCreate;
import com.jorados.diary.request.comment.CommentEdit;
import com.jorados.diary.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void create(CommentCreate commentCreate){
        Comment comment = Comment.builder()
                .content(commentCreate.getContent())
                .member(commentCreate.getMember())
                .post(commentCreate.getPost())
                .build();
        commentRepository.save(comment);
    }

    public CommentResponse read(Long commentId){
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new PostNotFound());
        CommentResponse commentResponse = CommentResponse.builder()
                .content(findComment.getContent())
                .member(findComment.getMember())
                .post(findComment.getPost())
                .build();
        return commentResponse;
    }

    @Transactional
    public void update(Long commentId, CommentEdit commentEdit) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new PostNotFound());
        findComment.edit(commentEdit.getContent() != null ? commentEdit.getContent() : findComment.getContent());
    }

    public void delete(Long commentId){
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new PostNotFound());
        commentRepository.delete(findComment);
    }
}
