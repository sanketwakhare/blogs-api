package com.sanket.blogsapi.services.slugs.exceptions;

import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SlugsExceptionHandler {

    @ExceptionHandler(SlugGenerationException.class)
    public ResponseEntity<ErrorResponseDTO> handleSlugGenerationException(SlugGenerationException exc) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exc.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
