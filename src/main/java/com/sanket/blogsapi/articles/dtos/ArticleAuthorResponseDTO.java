package com.sanket.blogsapi.articles.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuthorResponseDTO {
    @NonNull
    private String username;
}
