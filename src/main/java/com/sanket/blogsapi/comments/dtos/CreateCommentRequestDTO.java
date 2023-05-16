package com.sanket.blogsapi.comments.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequestDTO {
    @NonNull
    private String title;
    @NonNull
    private String body;
    @NonNull
    private UUID articleId;
}
