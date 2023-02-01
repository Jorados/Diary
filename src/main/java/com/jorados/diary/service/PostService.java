package com.jorados.diary.service;

import com.jorados.diary.domain.Post;
import com.jorados.diary.exception.PostNotFound;
import com.jorados.diary.model.Header;
import com.jorados.diary.model.Pagination;
import com.jorados.diary.model.SearchCondition;
import com.jorados.diary.repository.post.PostRepository;
import com.jorados.diary.request.post.PostCreate;
import com.jorados.diary.request.post.PostEdit;
import com.jorados.diary.request.post.PostSearch;
import com.jorados.diary.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void create(PostCreate postCreate,String username){
        Post post = Post.builder()
                .username(username)
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        postRepository.save(post);
    }

    public PostResponse read(Long postId){
        Post post = postRepository.findByIdComment(postId); //comment 패치조인 추가
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        return postResponse;
    }

    public List<PostResponse> findByMemberId(Long memberId){
        return postRepository.findByMemberId(memberId).stream()
                .map(post->new PostResponse(post))
                .collect(Collectors.toList());
    }

    //페이징 처리 만들고 나중에
    public List<PostResponse> readAll(PostSearch postSearch){
        return postRepository.getList(postSearch).stream()
                .map(post->new PostResponse(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long postId,PostEdit postEdit) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        findPost.edit(
                postEdit.getTitle() != null ? postEdit.getTitle() : findPost.getTitle(),
                postEdit.getContent() != null ? postEdit.getContent() : findPost.getContent()
        );
    }

    public void delete(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        postRepository.delete(findPost);
    }


    //vue 페이징
    public Header<List<PostResponse>> getBoardList(Pageable pageable, SearchCondition searchCondition) {
        List<PostResponse> dtos = new ArrayList<>();

        Page<Post> posts = postRepository.findAllBySearchCondition(pageable, searchCondition);
        for (Post post : posts) {
            PostResponse dto = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    //.createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();

            dtos.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) posts.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return Header.OK(dtos, pagination);
    }

}
