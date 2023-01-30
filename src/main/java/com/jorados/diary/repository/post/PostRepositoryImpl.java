package com.jorados.diary.repository.post;

import com.jorados.diary.domain.Post;
import com.jorados.diary.model.SearchCondition;
import com.jorados.diary.request.post.PostSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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

    public Page<Post> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition) {
        JPQLQuery<Post> query = queryFactory.selectFrom(post)
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()));

        long total = query.stream().count();   //여기서 전체 카운트 후 아래에서 조건작업

        List<Post> results = query
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression searchKeywords(String sk, String sv) {
        if("title".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return post.title.contains(sv);
            }
        } else if ("content".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return post.content.contains(sv);
            }
        }
        return null;
    }

}
