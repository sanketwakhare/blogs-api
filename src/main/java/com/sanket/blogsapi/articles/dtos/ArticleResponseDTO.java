package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.articles.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDTO {
    private UUID id;
    private String title;
    private String slug;
    private String subtitle;
    private String body;
    private ArticleStatus status;
    private Set<String> tags;
    private Set<ArticleAuthorResponseDTO> authors;
    private Date createdAt;
    private Date updatedAt;
}
