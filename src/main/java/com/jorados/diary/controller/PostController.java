package com.jorados.diary.controller;


import com.jorados.diary.model.Header;
import com.jorados.diary.model.SearchCondition;
import com.jorados.diary.repository.post.PostRepository;
import com.jorados.diary.request.post.PostCreate;
import com.jorados.diary.request.post.PostEdit;
import com.jorados.diary.request.post.PostSearch;
import com.jorados.diary.response.PostResponse;
import com.jorados.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/posts")
    public List<PostResponse> readAll(@ModelAttribute PostSearch postSearch){
        return postService.readAll(postSearch);
    }

    @PostMapping("/posts")
    public void create(@RequestBody @Valid PostCreate postCreate,
                       @AuthenticationPrincipal User user) throws Exception{
        String username = user.getUsername();
        log.info("username ={}",username);
        postService.create(postCreate,username);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse read(@PathVariable Long postId){
        return postService.read(postId);
    }

    @PatchMapping("/posts/{postId}")
    public void update(@PathVariable Long postId,@RequestBody PostEdit postEdit){
        postService.update(postId,postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

    //vue 페이징
//    @GetMapping("/board/list")
//    public List<PostResponse> boardList() {
//        return postService.getBoardList();
//    }

    @GetMapping("/board/list")
    public Header<List<PostResponse>> boardList(
            @PageableDefault(sort = {"id"}) Pageable pageable,
            SearchCondition searchCondition
    ) {
        return postService.getBoardList(pageable, searchCondition);
    }
}
