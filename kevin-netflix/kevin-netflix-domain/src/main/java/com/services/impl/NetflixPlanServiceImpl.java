package com.services.impl;

import com.exceptions.NetflixResourceNotFoundException;
import com.exceptions.netflixPlanExceptions.NetflixPlanPersistenceException;
import com.models.NetflixPlan;
import com.repositories.NetflixPlanModelRepository;
import com.services.NetflixPlanService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service( "kevinNetflixUserService" )
public class NetflixPlanServiceImpl implements NetflixPlanService {

    private final NetflixPlanModelRepository repository;

    public NetflixPlanServiceImpl( NetflixPlanModelRepository repository ) {
        this.repository = repository;
    }

    @Override
    public NetflixPlan createNetflixPlan( NetflixPlan netflixPlan ) {
        verifyValueNetflixPlan( netflixPlan );
        verifyNameNetflixPlan( netflixPlan );
        return repository.save( netflixPlan );
    }

    @Override
    public NetflixPlan updateNetflixPlan( NetflixPlan netflixPlan ) {
        AtomicReference<NetflixPlan> netflixPlanUpdated = new AtomicReference<>(null);
        repository.findById( netflixPlan.getId() ).ifPresentOrElse(
                ( plan ) -> {
                    verifyValueNetflixPlan( netflixPlan );
                    if ( !netflixPlan.getPlanName().equals(  plan.getPlanName() ) ){
                        verifyNameNetflixPlan( netflixPlan );
                    }
                    netflixPlanUpdated.set( repository.save( netflixPlan ) );
                },
                () -> { throw new NetflixResourceNotFoundException( netflixPlan.getId() ); }
        );
        return netflixPlanUpdated.get();
    }

    @Override
    public NetflixPlan findById( Integer id ) {
        return repository.findById( id ).orElseThrow( () -> { throw new NetflixResourceNotFoundException( id ); } );
    }

    @Override
    public void deleteById( Integer id ) {
        repository.deleteById( id );
    }

    private void verifyValueNetflixPlan( NetflixPlan netflixPlan ){
        if ( netflixPlan.getValue() < 0.0 ) {
            throw new NetflixPlanPersistenceException("Field value must be higher than zero");
        }
    }

    private void verifyNameNetflixPlan( NetflixPlan netflixPlan ){
        if ( repository.findByName( netflixPlan.getPlanName() ).isPresent() ) {
            throw new NetflixPlanPersistenceException("Name already in use");
        }
    }
}
