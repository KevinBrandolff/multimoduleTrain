package com.services;

import com.models.Profile;
import com.models.UserNetflix;

import java.util.List;

public interface ProfileService {

    Profile createProfile( Profile profile );
    Profile updateProfile( Profile profile );
    void deleteById( Integer id );
    List<Profile> findAll();
    Profile findById(Integer id );
    Profile addNetflixService( UserNetflix userNetflix, Integer profileId );

}
