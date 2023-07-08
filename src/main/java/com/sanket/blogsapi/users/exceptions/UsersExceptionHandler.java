package com.sanket.blogsapi.users.exceptions;

import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import com.sanket.blogsapi.common.dtos.FormFieldError;
import com.sanket.blogsapi.common.dtos.FormFieldErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UsersExceptionHandler {

    @ExceptionHandler({
            DuplicateUserEmailException.class,
            DuplicateUserNameException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleDuplicateUserException(Exception exc) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorMessage(exc.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler({InvalidCredentialsException.class,
            IncorrectAuthProviderException.class})
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(Exception exc) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorMessage(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException exc) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorMessage(exc.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler({
            UserCannotFollowException.class,
            UserCannotUnfollowException.class,
            UserAlreadyFollowedException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleUsersFollowUnfollowOperationException(Exception exc) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorMessage(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(PasswordLengthException.class)
    public ResponseEntity<FormFieldErrorResponseDTO> handlePasswordLengthException(PasswordLengthException exc) {
        // use error response format similar to constraint violation exception
        FormFieldErrorResponseDTO formFieldErrorResponseDTO = new FormFieldErrorResponseDTO();
        List<FormFieldError> errors = new ArrayList<>();
        errors.add(new FormFieldError("password", exc.getMessage()));
        formFieldErrorResponseDTO.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formFieldErrorResponseDTO);
    }
}
