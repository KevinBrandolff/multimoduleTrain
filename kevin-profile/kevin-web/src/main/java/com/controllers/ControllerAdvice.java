package com.controllers;

import com.dtos.ApiErrorsDTO;
import com.exceptions.NetflixModuleException;
import com.exceptions.ProfilePersistenceException;
import com.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleMethodNotValidException(MethodArgumentNotValidException ex ){
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(toList());
        return new ApiErrorsDTO(errors);
    }

    @ExceptionHandler( ProfilePersistenceException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleProfilePersistenceException( ProfilePersistenceException ex ){
        return new ApiErrorsDTO( ex.getMessage() );
    }

    @ExceptionHandler( ResourceNotFoundException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleResourceNotFoundException( ResourceNotFoundException ex ){
        return new ApiErrorsDTO( ex.getMessage() );
    }

    @ExceptionHandler( NetflixModuleException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleNetflixModuleException( NetflixModuleException ex ){
        return new ApiErrorsDTO( ex.getMessage() );
    }

}
