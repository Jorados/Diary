package com.jorados.diary.serviceTest;

import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.PostRepository;
import com.jorados.diary.request.PostCreate;
import com.jorados.diary.request.PostEdit;
import com.jorados.diary.request.PostSearch;
import com.jorados.diary.response.PostResponse;
import com.jorados.diary.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostServiceTest {

    @Autowired PostRepository postRepository;
    @Autowired PostService postService;

    @BeforeEach
    void deleteAll(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트")
    public void test1(){
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.create(postCreate);
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

}
