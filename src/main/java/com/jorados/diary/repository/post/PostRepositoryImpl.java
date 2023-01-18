package com.jorados.diary.repository.post;

import com.jorados.diary.domain.Post;
import com.jorados.diary.request.post.PostSearch;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jorados.diary.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPQLQueryFactory queryFactory;
    @Override
    public List<Post> getList(PostSearch postSearch) {
        return queryFactory
                .selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }


}
