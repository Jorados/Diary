package com.jorados.diary.serviceTest;

import com.jorados.diary.domain.Comment;
import com.jorados.diary.domain.Post;
import com.jorados.diary.domain.User;
import com.jorados.diary.exception.PostNotFound;
import com.jorados.diary.repository.comment.CommentRepository;
import com.jorados.diary.repository.post.PostRepository;
import com.jorados.diary.repository.user.UserRepository;
import com.jorados.diary.request.post.PostCreate;
import com.jorados.diary.request.post.PostEdit;
import com.jorados.diary.request.post.PostSearch;
import com.jorados.diary.response.PostResponse;
import com.jorados.diary.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostServiceTest {

    @Autowired PostRepository postRepository;
    @Autowired PostService postService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void deleteAll(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트")
    public void test1(){
        User user = User.builder()
                .userId("aaa")
                .userPw("bbb")
                .userName("ccc")
                .build();

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //postService.create(postCreate);
        assertThat(postCreate.getTitle()).isEqualTo("제목입니다.");
        assertThat(postCreate.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 1개 조회 테스트")
    public void test2(){
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        //when
        PostResponse postResponse = postService.read(post.getId());

        //then
        assertThat(postRepository.findAll().size()).isEqualTo(1);
        assertThat(postResponse.getTitle()).isEqualTo("제목입니다.");
        assertThat(postResponse.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 1페이지 조회")
    public void test2_2() throws Exception {
        //given
        List<Post> CreatePosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(CreatePosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();
        //when
        List<PostResponse> posts = postService.readAll(postSearch);
        //then
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("제목19");
    }

    @Test
    @DisplayName("글 수정")
    public void test3(){
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목2")
                .content("내용2")
                .build();
        //when
        postService.update(post.getId(),postEdit);

        //then
        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id " + post.getId()));

        assertThat(findPost.getTitle()).isEqualTo("제목2");
        assertThat(findPost.getContent()).isEqualTo("내용2");
    }

    @Test
    @DisplayName("글 삭제")
    public void test4(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("null"));
        postService.delete(findPost.getId());

        assertThat(postRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("글쓴이")
    public void test5() throws Exception {
        //given
        User user = User.builder()
                .userId("aa")
                .userPw("aa")
                .userName("aa")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("하하")
                .content("호호")
                .user(user)
                .build();

        postRepository.save(post);
        //when
        Post findPost = postRepository.findPostFetchJoin(post.getId());
        //then
        assertThat(findPost.getUser().getUserName()).isEqualTo("aa");
    }

    @Test
    @DisplayName("postId로 Post찾기. 근데 들어가 있는 comment도 같이 조회")
    public void test6() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);
        Comment comment = Comment.builder()
                .content("댓글1")
                .post(post)
                .build();
        commentRepository.save(comment);


        //when
        Post findPost = postRepository.findByIdComment(post.getId());
        //Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFound());
        //then
        assertThat(findPost.getComment().size()).isEqualTo(1);
        System.out.println(findPost.getComment().get(0));
    }

}
