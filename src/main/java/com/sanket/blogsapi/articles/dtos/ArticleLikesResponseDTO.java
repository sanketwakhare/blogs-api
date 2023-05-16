package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikesResponseDTO {
     private Set<ArticleLikedByUserResponseDTO> likedBy = new HashSet<>();
     private int likesCount = 0;
}
