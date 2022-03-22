package com.repositories.impl;

import com.entities.NetflixPlanEntity;
import com.entities.UserNetflixEntity;
import com.models.NetflixPlan;
import com.models.UserNetflix;
import com.repositories.UserNetflixEntityRepository;
import com.repositories.UserNetflixModelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserNetflixModelRepositoryImpl implements UserNetflixModelRepository {

    private final UserNetflixEntityRepository repository;

    public UserNetflixModelRepositoryImpl( UserNetflixEntityRepository repository ) {
        this.repository = repository;
    }

    @Override
    public UserNetflix save(UserNetflix userNetflix) {
        return mapUserNetflixEntityToModel( repository.save( mapUserNetflixModelToEntity( userNetflix ) ) );
    }

    @Override
    public Optional<UserNetflix> findByUsername(String username) {
        AtomicReference<UserNetflixEntity> userNetflixEntity = new AtomicReference<>(null);
        repository.findByUsername( username ).ifPresent( (user) -> { userNetflixEntity.set( user ); } );
        return Optional.ofNullable( mapUserNetflixEntityToModel( userNetflixEntity.get() ) );
    }

    @Override
    public Optional<UserNetflix> findById(Integer id) {
        AtomicReference<UserNetflixEntity> userNetflixEntity = new AtomicReference<>(null);
        repository.findById( id ).ifPresent( (user) -> { userNetflixEntity.set( user ); } );
        return Optional.ofNullable( mapUserNetflixEntityToModel( userNetflixEntity.get() ) );
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById( id );
    }

    private UserNetflixEntity mapUserNetflixModelToEntity( UserNetflix userNetflix ) {
        if ( userNetflix == null ) return null;
        NetflixPlanEntity netflixPlanEntity = null;
        if ( userNetflix.getNetflixPlan() != null ){
            netflixPlanEntity = NetflixPlanEntity.builder().build();
            BeanUtils.copyProperties( userNetflix.getNetflixPlan(), netflixPlanEntity );
        }
        return UserNetflixEntity.builder()
                .id( userNetflix.getId() )
                .username( userNetflix.getUsername() )
                .password( userNetflix.getPassword() )
                .role( convertRoleStringToEnum( userNetflix.getRole() ) )
                .netflixPlan( netflixPlanEntity )
                .build();
    }

    private UserNetflix mapUserNetflixEntityToModel( UserNetflixEntity userNetflixEntity ) {
        if ( userNetflixEntity == null ) return null;
        NetflixPlan netflixPlan = null;
        if ( userNetflixEntity.getNetflixPlan() != null ){
            netflixPlan = NetflixPlan.builder().build();
            BeanUtils.copyProperties( userNetflixEntity.getNetflixPlan(), netflixPlan );
        }
        return UserNetflix.builder()
                .id( userNetflixEntity.getId() )
                .username( userNetflixEntity.getUsername() )
                .password( userNetflixEntity.getPassword() )
                .role( userNetflixEntity.getRole().toString() )
                .netflixPlan( netflixPlan )
                .build();
    }

    private UserNetflixEntity.RoleEnum convertRoleStringToEnum( String roleString ) {
        for ( UserNetflixEntity.RoleEnum role : UserNetflixEntity.RoleEnum.values() ){
            if ( role.toString().equals( roleString.toUpperCase() ) ) {
                return role;
            }
        }
        return UserNetflixEntity.RoleEnum.USER;
    }
}
