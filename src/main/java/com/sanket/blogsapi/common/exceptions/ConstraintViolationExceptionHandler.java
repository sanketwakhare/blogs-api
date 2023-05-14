package com.sanket.blogsapi.common.exceptions;

import com.sanket.blogsapi.common.dtos.FormFieldError;
import com.sanket.blogsapi.common.dtos.FormFieldErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FormFieldErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        FormFieldErrorResponseDTO formFieldErrorResponseDTO = new FormFieldErrorResponseDTO();
        List<FormFieldError> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(
                error -> errors.add(new FormFieldError(
                        error.getPropertyPath().toString(),
                        error.getMessage()))
        );
        formFieldErrorResponseDTO.setErrors(errors);
        return ResponseEntity.badRequest().body(formFieldErrorResponseDTO);
    }
}
