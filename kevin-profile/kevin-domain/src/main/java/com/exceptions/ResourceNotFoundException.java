package com.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource not found");
    }

    public ResourceNotFoundException( Integer id ){
        super( "Id " + id + " not found!");
    }

}
