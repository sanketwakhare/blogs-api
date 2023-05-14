package com.sanket.blogsapi.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldError {
    private String field;
    private String errorMessage;
}
