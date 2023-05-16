package com.sanket.blogsapi.articles.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequestDTO {
    @NonNull
    private String title;
    private String subtitle;
    @NonNull
    private String body;
    private Set<String> tags;
}
