package com.jorados.diary.serviceTest;

import com.jorados.diary.repository.user.UserRepository;
import com.jorados.diary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @BeforeEach
    void deleteAll(){
        userRepository.deleteAll();
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
}
