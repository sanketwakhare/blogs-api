package com.sanket.blogsapi.common.exceptions;

import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import com.sanket.blogsapi.common.dtos.FormFieldError;
import com.sanket.blogsapi.common.dtos.FormFieldErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exc) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exc.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FormFieldErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exc) {
//        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exc.getCause().getMessage());
        FormFieldErrorResponseDTO formFieldErrorResponseDTO = new FormFieldErrorResponseDTO();
        List<FormFieldError> errors = new ArrayList<>();
        errors.add(new FormFieldError(exc.getName(), exc.getCause().getMessage()));
        formFieldErrorResponseDTO.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formFieldErrorResponseDTO);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException exc) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(exc.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
