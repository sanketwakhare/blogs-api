package com.sanket.blogsapi.roles.exceptions;

import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RolesExceptionHandler {

    @ExceptionHandler({
            RoleAlreadyExistsException.class,
            RoleNotFoundException.class,
            RoleAlreadyAssignedException.class,
            UserRoleNotPresentException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleRolesBadRequestsException(Exception e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorMessage(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
}
