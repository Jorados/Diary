package com.jorados.diary.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;
    private Integer page = 1;
    private Integer size = 10;

    public long getOffset(){
        return (long) (Math.max(1,page) -1) * Math.min(size,MAX_SIZE);
    }
}
