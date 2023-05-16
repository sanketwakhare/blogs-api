package com.sanket.blogsapi.comments.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequestDTO {
    @NonNull
    private String title;
    @NonNull
    private String body;
}
