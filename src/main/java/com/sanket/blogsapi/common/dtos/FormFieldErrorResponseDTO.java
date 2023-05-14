package com.sanket.blogsapi.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldErrorResponseDTO {
    List<FormFieldError> errors;
}
