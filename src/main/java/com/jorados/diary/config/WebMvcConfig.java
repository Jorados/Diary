package com.jorados.diary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
//Pageable 에서 리스트 인덱싱을 할때 0부터 시작하게 됩니다.
//우리는 1페이지를 눌렀지만 Pageable에는 0으로 입력되어야 합니다.
//그러나 매번 처리하기에는 복잡하므로 Config 클래스를 생성해서 작업하겠습니다.
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        SortHandlerMethodArgumentResolver sortArgumentResolver = new SortHandlerMethodArgumentResolver();
        sortArgumentResolver.setSortParameter("sortBy");
        sortArgumentResolver.setPropertyDelimiter("-");

        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver(sortArgumentResolver);
        pageableArgumentResolver.setOneIndexedParameters(true);
        pageableArgumentResolver.setMaxPageSize(500);
        pageableArgumentResolver.setFallbackPageable(PageRequest.of(0, 5));
        argumentResolvers.add(pageableArgumentResolver);
    }
}
