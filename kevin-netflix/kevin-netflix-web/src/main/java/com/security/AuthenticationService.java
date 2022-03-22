package com.security;

import com.entities.UserNetflixEntity;
import com.repositories.UserNetflixEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserNetflixEntityRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserNetflixEntity user = this.repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found!") );

        String[] roles = new String[1];

        if ( user.getRole().equals( UserNetflixEntity.RoleEnum.ADMIN ) ) {
            roles[0] = "ADMIN";
        }else if ( user.getRole().equals( UserNetflixEntity.RoleEnum.USER ) ) {
            roles[0] = "USER";
        }

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();

    }



}
