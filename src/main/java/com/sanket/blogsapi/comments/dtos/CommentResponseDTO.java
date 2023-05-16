package com.sanket.blogsapi.comments.dtos;

import com.sanket.blogsapi.users.dtos.BasicUserDetailsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    @NonNull
    private UUID id;
    @NonNull
    private UUID articleId;
    @NonNull
    private String title;
    @NonNull
    private String body;
    @NonNull
    private BasicUserDetailsResponseDTO author;
    @NonNull
    private Date createdAt;
    @NonNull
    private Date updatedAt;
}
