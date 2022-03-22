package com.services.impl;

import com.exceptions.NetflixResourceNotFoundException;
import com.exceptions.userNetflixExceptions.UserNetflixPersistenceException;
import com.models.UserNetflix;
import com.repositories.NetflixPlanModelRepository;
import com.repositories.UserNetflixModelRepository;
import com.services.UserNetflixService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service( "kevinUserNetflixService" )
public class UserNetflixServiceImpl implements UserNetflixService {

    private final UserNetflixModelRepository repository;
    private final NetflixPlanModelRepository planRepository;

    public UserNetflixServiceImpl(UserNetflixModelRepository repository, NetflixPlanModelRepository planRepository) {
        this.repository = repository;
        this.planRepository = planRepository;
    }

    @Override
    public UserNetflix createUserNetflix(UserNetflix userNetflix) {
        verifyUsername( userNetflix.getUsername() );
        userNetflix.setRole( "USER" );
        userNetflix.setNetflixPlan(null);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        userNetflix.setPassword( bcrypt.encode(userNetflix.getPassword() ) );
        UserNetflix userNetflixSaved =  repository.save( userNetflix );
        userNetflixSaved.setPassword(null);
        return userNetflixSaved;
    }

    @Override
    public UserNetflix updateUserNetflix(UserNetflix userNetflix) {
        AtomicReference<UserNetflix> userUpdated = new AtomicReference<>(null);

        repository.findById( userNetflix.getId() ).ifPresentOrElse(
                ( user ) -> {
                    if ( !userNetflix.getUsername().equals( user.getUsername() ) ) {
                        verifyUsername( userNetflix.getUsername() );
                    }
                    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                    userNetflix.setPassword( bcrypt.encode( userNetflix.getPassword() ) );
                    userNetflix.setRole( user.getRole() );
                    userNetflix.setNetflixPlan( user.getNetflixPlan() );
                    userUpdated.set(repository.save(userNetflix));
                },
                () -> { throw new NetflixResourceNotFoundException( userNetflix.getId() ); }
        );

        userUpdated.get().setPassword(null);
        return userUpdated.get();
    }

    @Override
    public UserNetflix findById(Integer id) {
        UserNetflix userNetflix =  repository.findById( id ).orElseThrow( () -> { throw new NetflixResourceNotFoundException( id ); } );
        userNetflix.setPassword(null);
        return userNetflix;
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById( id );
    }

    @Override
    public UserNetflix addPlanToUser( String username, String planName ) {
        AtomicReference<UserNetflix> userNetflix = new AtomicReference<>(null);

        repository.findByUsername( username )
                .ifPresentOrElse( ( user ) -> { planRepository.findByName( planName )
                                .ifPresentOrElse( ( plan ) -> {
                                        user.setNetflixPlan( plan );
                                        userNetflix.set(repository.save(user));
                                    },
                                    () -> { throw new NetflixResourceNotFoundException("Plan not found in data base!"); }
                                );
                    },
                    () -> { throw new NetflixResourceNotFoundException("Username not found in data base!"); }
                );

        userNetflix.get().setPassword(null);
        return userNetflix.get();
    }

    private void verifyUsername( String username ) {
        if ( repository.findByUsername( username ).isPresent() ) {
            throw new UserNetflixPersistenceException("Username already in use");
        }
    }
}
