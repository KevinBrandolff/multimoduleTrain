package com.services.impl;

import com.exceptions.NetflixModuleException;
import com.models.User;
import com.models.UserNetflix;
import com.services.NetflixServiceConnector;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.Collections;

@Service
public class NetflixServiceConnectorImpl implements NetflixServiceConnector {

    private static RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "http://localhost:8083/kevin";

    @Override
    public ResponseEntity<UserNetflix> registerNetflixService(UserNetflix userNetflix) {
        try {
            return restTemplate.postForEntity(URL+"/userNetflix/", userNetflix, UserNetflix.class);
        }catch ( Exception e ) {
            throw new NetflixModuleException( e.getMessage() );
        }
    }

    @Override
    public ResponseEntity<Object> loginNetflixService(User user) {
        return restTemplate.postForEntity(URL+"/login", user, Object.class);
    }

    @Override
    public ResponseEntity<UserNetflix> addNetflixPlanNetflixService(UserNetflix userNetflix, String token) {
        HttpEntity request = new HttpEntity( getHeaders(token) );
        return restTemplate.postForEntity(URL+"/userNetflix/plan/"+userNetflix.getPlanName(), request, UserNetflix.class);
    }

    @Override
    public ResponseEntity<UserNetflix> updateUserNetflixService(UserNetflix userNetflix, String token) {
        HttpEntity<UserNetflix> request = new HttpEntity<>( userNetflix, getHeaders(token) );
        return restTemplate.exchange(URL+"/userNetflix/", HttpMethod.PUT, request, UserNetflix.class);
    }

    @Override
    public ResponseEntity<Object> recover( User user, ConnectException e) {
        throw new NetflixModuleException( "Error occurred trying connect to Netflix service." );
    }

    private HttpHeaders getHeaders(String token ){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", token);
        return headers;
    }
}
