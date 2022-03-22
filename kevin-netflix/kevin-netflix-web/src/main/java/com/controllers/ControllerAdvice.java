package com.controllers;

import com.dtos.ApiErrorsDTO;
import com.exceptions.NetflixResourceNotFoundException;
import com.exceptions.netflixPlanExceptions.NetflixPlanPersistenceException;
import com.exceptions.userNetflixExceptions.UserNetflixPersistenceException;
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

    @ExceptionHandler( NetflixResourceNotFoundException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleNetflixResourceNotFoundException( NetflixResourceNotFoundException ex ){
        return new ApiErrorsDTO( ex.getMessage() );
    }

    @ExceptionHandler( NetflixPlanPersistenceException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleNetflixPlanPersistenceException( NetflixPlanPersistenceException ex ){
        return new ApiErrorsDTO( ex.getMessage() );
    }

    @ExceptionHandler( UserNetflixPersistenceException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ApiErrorsDTO handleUserNetflixPersistenceException( UserNetflixPersistenceException ex ){
        return new ApiErrorsDTO( ex.getMessage() );
    }

}
