package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikesResponseDTO {
     @NonNull
     private UUID articleId;
     @NonNull
     private Set<ArticleLikedByUserResponseDTO> likedBy = new HashSet<>();
     private int likesCount = 0;
}
