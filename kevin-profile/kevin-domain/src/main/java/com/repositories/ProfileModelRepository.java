package com.repositories;

import com.models.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileModelRepository {

    Profile save( Profile profile );
    void deleteById( Integer id );
    Optional<Profile> findById( Integer id );
    List<Profile> findAll();

}
