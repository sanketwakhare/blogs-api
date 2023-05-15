package com.sanket.blogsapi.articles.exceptions;

import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArticlesExceptionHandler {

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleArticleNotFoundException(ArticleNotFoundException exception) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }
}
