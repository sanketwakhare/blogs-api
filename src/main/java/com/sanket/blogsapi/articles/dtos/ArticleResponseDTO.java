package com.sanket.blogsapi.articles.dtos;

import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.dtos.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private List<UserResponseDTO> authors;
    // TODO: implement likes api
    // likes can be used to show the number of likes for an article.
    // likes need not be fetched immediately
    // private List<UserEntity> likedBy;
}
