package com.services;

import com.models.User;
import com.models.UserNetflix;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.net.ConnectException;

public interface NetflixServiceConnector {

    @Retryable( value = ConnectException.class, maxAttempts=4, backoff=@Backoff( value = 3000, multiplier = 2 ))
    ResponseEntity<UserNetflix> registerNetflixService( UserNetflix userNetflix );
    @Retryable( value = ConnectException.class, maxAttempts=4, backoff=@Backoff( value = 3000, multiplier = 2 ))
    ResponseEntity<Object> loginNetflixService(User user );
    @Retryable( value = ConnectException.class, maxAttempts=4, backoff=@Backoff( value = 3000, multiplier = 2 ))
    ResponseEntity<UserNetflix> addNetflixPlanNetflixService(UserNetflix userNetflix, String token );
    @Retryable( value = ConnectException.class, maxAttempts=4, backoff=@Backoff( value = 3000, multiplier = 2 ))
    ResponseEntity<UserNetflix> updateUserNetflixService( UserNetflix userNetflix, String token );
    @Recover
    ResponseEntity<Object> recover( User user, ConnectException e );

}
