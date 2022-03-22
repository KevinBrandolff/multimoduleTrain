package com.repositories;

import com.models.UserNetflix;

import java.util.Optional;

public interface UserNetflixModelRepository {

    UserNetflix save( UserNetflix userNetflix );
    Optional<UserNetflix> findByUsername(String username );
    Optional<UserNetflix> findById( Integer id );
    void deleteById( Integer id );

}
