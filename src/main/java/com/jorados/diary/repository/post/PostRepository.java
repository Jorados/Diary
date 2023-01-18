package com.jorados.diary.repository.post;

import com.jorados.diary.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>,PostRepositoryCustom {
    Post findByTitle(String title);

    @Query("select p from Post p left join fetch p.member")
    Post findPostFetchJoin(Long postId);

    @Query("select p from Post p where p.member.id = :memberId")
    List<Post> findByMemberId(@Param("memberId") Long memberId);

}
