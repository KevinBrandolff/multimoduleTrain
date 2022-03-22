package com.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class JWTToken {

    static final long EXPIRATION_TIME = 7_200_000;
    static final String SECRET = "secret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse response, String username){
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date( System.currentTimeMillis() + EXPIRATION_TIME ))
                .signWith( SignatureAlgorithm.HS512, SECRET )
                .compact();

        response.addHeader( HEADER_STRING, TOKEN_PREFIX + " " + JWT );
    }

    static Authentication getAuthentication(HttpServletRequest request, UserDetails userDetails, String user ){

        if( user != null ){
            return new UsernamePasswordAuthenticationToken( user, null, userDetails.getAuthorities() );
        }

        return null;
    }

    static String getUsername( HttpServletRequest request ){
        String token = request.getHeader(HEADER_STRING);
        String user = null;

        if( token != null ) {
            user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws( token.replace( TOKEN_PREFIX, "" ) )
                    .getBody()
                    .getSubject();
        }
        return user;
    }

}
