package com.exceptions;

public class NetflixResourceNotFoundException extends RuntimeException{

    public NetflixResourceNotFoundException( String msg ) {
        super( msg );
    }

    public NetflixResourceNotFoundException( Integer id ) {
        super( "resource with id " + id + " not found!" );
    }

}
