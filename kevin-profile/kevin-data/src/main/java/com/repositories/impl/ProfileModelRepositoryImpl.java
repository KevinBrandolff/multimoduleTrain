package com.repositories.impl;

import com.entities.ProfileEntity;
import com.entities.ServiceAccountsEntity;
import com.models.Profile;
import com.models.ServiceAccounts;
import com.repositories.ProfileEntityRepository;
import com.repositories.ProfileModelRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component( value = "kevinProfileModelRepositoryImpl" )
public class ProfileModelRepositoryImpl implements ProfileModelRepository {

    private ProfileEntityRepository repository;

    public ProfileModelRepositoryImpl( ProfileEntityRepository repository ){
        this.repository = repository;
    }

    @Override
    public Profile save(Profile profile) {
        ProfileEntity profileEntity = repository.save( mapProfileModelToEntity( profile ) );
        return mapProfileEntityToModel( profileEntity );
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById( id );
    }

    @Override
    public Optional<Profile> findById(Integer id) {
        AtomicReference<ProfileEntity> profileEntity = new AtomicReference<>(null);
        repository.findById( id ).ifPresent( (profile) -> { profileEntity.set( profile ); } );
        return Optional.ofNullable( mapProfileEntityToModel( profileEntity.get() ) );
    }

    @Override
    public List<Profile> findAll() {
        return mapListProfileEntityToModel( repository.findAll() );
    }

    private Profile mapProfileEntityToModel( ProfileEntity profileEntity ) {
        if ( profileEntity == null ) return null;
        return Profile.builder()
                .id( profileEntity.getId() )
                .name( profileEntity.getName() )
                .age( profileEntity.getAge() )
                .interests( profileEntity.getInterests() )
                .createAt( profileEntity.getCreatedAt() )
                .updatedAt( profileEntity.getUpdatedAt() )
                .serviceAccounts( mapServiceAccountsEntityToModel( profileEntity.getServiceAndCredentials() ) )
                .build();
    }

    private ProfileEntity mapProfileModelToEntity( Profile profile ) {
        if ( profile == null ) return null;
        return ProfileEntity.builder()
                .id( profile.getId() )
                .name( profile.getName() )
                .age( profile.getAge() )
                .interests( profile.getInterests() )
                .createdAt( profile.getCreateAt() )
                .updatedAt( profile.getUpdatedAt() )
                .serviceAndCredentials( mapServiceAccountsModelToEntity( profile.getServiceAccounts() ) )
                .build();
    }

    private List<Profile> mapListProfileEntityToModel( List<ProfileEntity> profilesEntity ) {
        if ( profilesEntity == null ) return null;

        List<Profile> profiles = new ArrayList<>();
        for( ProfileEntity profileEntity : profilesEntity ) {
            Profile profile = Profile.builder()
                    .id( profileEntity.getId() )
                    .name( profileEntity.getName() )
                    .age( profileEntity.getAge() )
                    .interests( profileEntity.getInterests() )
                    .createAt( profileEntity.getCreatedAt() )
                    .updatedAt( profileEntity.getUpdatedAt() )
                    .build();
            profiles.add( profile );
        }
        return profiles;
    }

    private List<ServiceAccountsEntity> mapServiceAccountsModelToEntity( List<ServiceAccounts> serviceAccounts) {
        if ( serviceAccounts == null ) return null;

        List<ServiceAccountsEntity> services = new ArrayList<>();
        for ( ServiceAccounts serviceAccount : serviceAccounts ) {
            ServiceAccountsEntity serviceAccountsEntity = ServiceAccountsEntity.builder()
                    .id( serviceAccount.getId() )
                    .service( serviceAccount.getService() )
                    .plan( serviceAccount.getPlan() )
                    .username( serviceAccount.getUsername() )
                    .password( serviceAccount.getPassword() )
                    .idUserNetflix( serviceAccount.getIdUserNetflix() )
                    .profile( ProfileEntity.builder().id( serviceAccount.getProfile().getId() ).build() )
                    .build();
            services.add( serviceAccountsEntity );
        }
        return services;
    }

    private List<ServiceAccounts> mapServiceAccountsEntityToModel( List<ServiceAccountsEntity> serviceAccountsEntity ) {
        if ( serviceAccountsEntity == null ) return null;

        List<ServiceAccounts> services = new ArrayList<>();
        for ( ServiceAccountsEntity serviceAccountEntity : serviceAccountsEntity ) {
            ServiceAccounts serviceAccount = ServiceAccounts.builder()
                    .id( serviceAccountEntity.getId() )
                    .service( serviceAccountEntity.getService() )
                    .plan( serviceAccountEntity.getPlan() )
                    .username( serviceAccountEntity.getUsername() )
                    .password( serviceAccountEntity.getPassword() )
                    .idUserNetflix( serviceAccountEntity.getIdUserNetflix() )
                    .build();
            services.add( serviceAccount );
        }
        return services;
    }
}
