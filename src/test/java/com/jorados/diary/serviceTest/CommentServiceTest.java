package com.jorados.diary.serviceTest;

import com.jorados.diary.domain.Comment;
import com.jorados.diary.domain.Post;
import com.jorados.diary.domain.User;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.repository.post.PostRepository;
import com.jorados.diary.repository.user.UserRepository;
import com.jorados.diary.response.CommentResponse;
import com.jorados.diary.service.CommentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {
    @BeforeEach
    void deleteAll() { commentRepository.deleteAll();}
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("comment -> member,post 조회")
    public void test1() throws Exception {
        //given
       User user = User.builder()
               .userId("1")
               .userPw("1")
               .userName("1")
               .build();
        userRepository.save(user);

        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .user(user)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .content("제목1의 댓글")
                .user(user)
                .post(post)
                .build();
        commentRepository.save(comment);
        //when
        //CommentResponse findComment = commentService.read(comment.getId());
        Comment findComment = commentRepository.findByCommentId(comment.getId());
        //then
        assertThat(findComment.getContent()).isEqualTo("제목1의 댓글");
        assertThat(findComment.getUser().getUserName()).isEqualTo("1"); //조인필요 -> 어찌저찌 해결
        assertThat(findComment.getPost().getTitle()).isEqualTo("제목1"); //조인필요 -> 어찌저찌 해결
    }

    @Test
    @DisplayName("postId로 해당 comment 다 조회")
    public void test2() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .content("내용")
                .post(post)
                .build();
        commentRepository.save(comment);
        //when
        List<Comment> findComment = commentRepository.findByCommentPostId(post.getId());
        //then
        assertThat(findComment.get(0).getPost().getTitle()).isEqualTo("제목");
    }
}
