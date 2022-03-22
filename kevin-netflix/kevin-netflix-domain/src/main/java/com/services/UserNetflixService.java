package com.services;

import com.models.UserNetflix;

public interface UserNetflixService {

    UserNetflix createUserNetflix( UserNetflix userNetflix );
    UserNetflix updateUserNetflix( UserNetflix userNetflix );
    UserNetflix findById( Integer id );
    void deleteById( Integer id );
    UserNetflix addPlanToUser( String username, String planName );

}
