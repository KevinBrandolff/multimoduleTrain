package com.dtos;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrorsDTO {

    private List<String> errors;

    public ApiErrorsDTO( String errorMenssage ) {
        this.errors = Arrays.asList(errorMenssage);
    }

    public ApiErrorsDTO( List<String> errors ) {
        this.errors = errors;
    }

}
