package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.common.sort.SortCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesFilterCriteriaRequestDTO {
    private UUID authorId;
    private Set<String> tags;
    private SortCriteria sortCriteria;
    private Date fromDate;
    private Date toDate;
}
