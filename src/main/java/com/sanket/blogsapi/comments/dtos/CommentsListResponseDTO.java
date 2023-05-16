package com.sanket.blogsapi.comments.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsListResponseDTO {
    private List<CommentResponseDTO> comments;
}
