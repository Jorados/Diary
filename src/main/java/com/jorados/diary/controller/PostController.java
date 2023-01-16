package com.jorados.diary.controller;


import com.jorados.diary.domain.Post;
import com.jorados.diary.request.PostCreate;
import com.jorados.diary.request.PostSearch;
import com.jorados.diary.response.PostResponse;
import com.jorados.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostResponse> readAll(@ModelAttribute PostSearch postSearch){
        return postService.readAll(postSearch);
    }

    @PostMapping("/posts")
    public void create(@RequestBody @Valid PostCreate postCreate) throws Exception{
        postService.create(postCreate);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse read(@PathVariable Long postId){
        return postService.read(postId);
    }


}
