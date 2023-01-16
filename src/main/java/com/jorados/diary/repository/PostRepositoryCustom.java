package com.jorados.diary.repository;

import com.jorados.diary.domain.Post;
import com.jorados.diary.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
