package com.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;
    private final AccessDeniedHandler accessDeniedHandler;

    public JWTAuthenticationFilter( AuthenticationService authenticationService, AccessDeniedHandler accessDeniedHandler ) {
        this.authenticationService = authenticationService;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-control-allow-origin", "*");
        res.setHeader("Access-control-allow-Methods", "POST, PUT, GET, DELETE");
        res.setHeader("Access-control-Max-Age", "3600");
        res.setHeader("Access-control-allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        try {
            String username = JWTToken.getUsername( (HttpServletRequest) request );
            UserDetails userDetails = username != null ? authenticationService.loadUserByUsername( username ) : null;
            Authentication authentication = JWTToken.getAuthentication( (HttpServletRequest) request, userDetails, username );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }catch ( Exception e ) {
            accessDeniedHandler.handle( (HttpServletRequest) request, (HttpServletResponse) response, new AccessDeniedException(e.getMessage()));
        }

    }


}
