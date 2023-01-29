package com.jorados.diary.service;


import com.jorados.diary.domain.Comment;
import com.jorados.diary.domain.Post;
import com.jorados.diary.exception.PostNotFound;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.repository.post.PostRepository;
import com.jorados.diary.request.comment.CommentCreate;
import com.jorados.diary.request.comment.CommentEdit;
import com.jorados.diary.response.CommentResponse;
import com.jorados.diary.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(CommentCreate commentCreate){
        Comment comment = Comment.builder()
                .content(commentCreate.getContent())
                .user(commentCreate.getUser())
                .post(commentCreate.getPost())
                .build();
        commentRepository.save(comment);
    }

    public CommentResponse read(Long commentId){
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new PostNotFound());
        CommentResponse commentResponse = CommentResponse.builder()
                .content(findComment.getContent())
                .user(findComment.getUser())
                .post(findComment.getPost())
                .build();
        return commentResponse;
    }

    public List<Comment> readAll(Long postId){
        List<Comment> findComment = commentRepository.findByCommentPostId(postId);
        return findComment;
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
