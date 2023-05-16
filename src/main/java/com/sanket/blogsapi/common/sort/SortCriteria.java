package com.sanket.blogsapi.common.sort;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortCriteria {

    // TODO: add validation for field
    // TODO: add different strategies for sorting based on field
    // private String field;

    @Enumerated(value = EnumType.STRING)
    private SortDirection direction = SortDirection.ASC;
}
