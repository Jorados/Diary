package com.jorados.diary.service;

import com.jorados.diary.domain.Post;
import com.jorados.diary.repository.PostRepository;
import com.jorados.diary.request.PostCreate;
import com.jorados.diary.request.PostEdit;
import com.jorados.diary.request.PostSearch;
import com.jorados.diary.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void create(PostCreate postCreate){
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        postRepository.save(post);
    }

    public PostResponse read(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException());
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        return postResponse;
    }

    //페이징 처리 만들고 나중에
    public List<PostResponse> readAll(PostSearch postSearch){
        return postRepository.getList(postSearch).stream()
                .map(post->new PostResponse(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long postId,PostEdit postEdit) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        findPost.edit(
                postEdit.getTitle() != null ? postEdit.getTitle() : findPost.getTitle(),
                postEdit.getContent() != null ? postEdit.getContent() : findPost.getContent()
        );
    }

    public void delete(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        postRepository.delete(findPost);
    }





}
