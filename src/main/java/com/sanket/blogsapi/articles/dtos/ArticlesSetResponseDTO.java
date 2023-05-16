package com.sanket.blogsapi.articles.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesSetResponseDTO {
    private Set<ArticleResponseDTO> articles;
}
