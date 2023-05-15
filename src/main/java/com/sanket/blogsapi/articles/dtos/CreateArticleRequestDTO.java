package com.sanket.blogsapi.articles.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequestDTO {
    @NonNull
    private String title;
    private String subtitle;
    @NonNull
    private String body;
}
