package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.articles.ArticleReactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReactionResponseDTO {
    private UUID articleId;
    private UUID userId;
    @Enumerated(value = EnumType.STRING)
    private ArticleReactionType reaction;
}
