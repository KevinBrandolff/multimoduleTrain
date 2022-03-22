package com.services.impl;

import com.exceptions.ProfilePersistenceException;
import com.exceptions.ResourceNotFoundException;
import com.models.Profile;
import com.models.UserNetflix;
import com.repositories.ProfileModelRepository;
import com.services.ManagementServiceAccounts;
import com.services.ProfileService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service( value = "kevinProfileServiceImpl" )
public class ProfileServiceImpl implements ProfileService {

    private final ProfileModelRepository repository;
    private final ManagementServiceAccounts managementServiceAccounts;

    public ProfileServiceImpl(ProfileModelRepository profileModelRepository, ManagementServiceAccounts managementServiceAccounts) {
        this.repository = profileModelRepository;
        this.managementServiceAccounts = managementServiceAccounts;
    }

    @Override
    public Profile createProfile( Profile profile ) {
        profile.setCreateAt( LocalDate.now() );
        return repository.save( profile );
    }

    @Override
    public Profile updateProfile( Profile profile ) {
        Optional<Profile> profileChecked = repository.findById( profile.getId() );
        if ( !profileChecked.isPresent() ) {
            throw new ProfilePersistenceException( "Profile with id " + profile.getId() + " don't exist!" );
        }
        profile.setCreateAt( profileChecked.get().getCreateAt() );
        profile.setUpdatedAt( LocalDate.now() );
        return repository.save( profile );
    }

    @Override
    public Profile findById(Integer id) {
        return repository.findById( id ).orElseThrow( () -> new ResourceNotFoundException( id ) );
    }

    @Override
    public void deleteById( Integer id ) {
        repository.deleteById( id );
    }

    @Override
    public List<Profile> findAll() {
        return repository.findAll();
    }

    @Override
    public Profile addNetflixService( UserNetflix userNetflix, Integer profileId ) {
        return managementServiceAccounts.addNetflixService( userNetflix, profileId );
    }

}