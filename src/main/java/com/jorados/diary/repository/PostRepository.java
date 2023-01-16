package com.jorados.diary.repository;

import com.jorados.diary.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>,PostRepositoryCustom {
    Post findByTitle(String title);
}
