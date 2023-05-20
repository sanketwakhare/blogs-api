package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.articles.ArticleReactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReactionResponseDTO {
    @NonNull
    private UUID articleId;
    @NonNull
    private String username;
    @NonNull
    @Enumerated(value = EnumType.STRING)
    private ArticleReactionType reaction;
}
