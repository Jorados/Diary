package com.jorados.diary.repository.comment;

import com.jorados.diary.domain.Comment;
import com.jorados.diary.domain.Member;
import com.jorados.diary.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //commentId로 member와 post를 join하는 특정 comment조회
    //문제점 --> 한번에 특정 두 테이블 이상 패치 조인이 안됨.
    //-> distinct로 중복제거하면서 조인 -> 이래도 되는지 모르겟다
    @Query("select distinct  c from Comment c left join fetch c.post left join fetch c.member where c.id = :commentId")
    Comment findByCommentId(@Param("commentId") Long commentId);
}
