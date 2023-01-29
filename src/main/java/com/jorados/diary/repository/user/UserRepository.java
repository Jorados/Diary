package com.jorados.diary.repository.user;

/* UserRepository.java */

import com.jorados.diary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    List<User> findAllByUserName(String userName);
    List<User> findAllByUserId(String userId);
}
