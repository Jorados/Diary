package com.jorados.diary.repository.post;

import com.jorados.diary.domain.Post;
import com.jorados.diary.model.SearchCondition;
import com.jorados.diary.request.post.PostSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
    Page<Post> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition);
}
