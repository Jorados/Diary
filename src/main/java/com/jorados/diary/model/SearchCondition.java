package com.jorados.diary.model;

/* model/SearchCondition.java */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCondition {
    private String sk;  //search key
    private String sv;  //search value
}
