package com.jorados.diary.repository.post;

import com.jorados.diary.domain.Post;
import com.jorados.diary.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
