package com.jorados.diary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    public String test(){
        return "<h1>잘해보자 성진아.<h1>";
    }
}
