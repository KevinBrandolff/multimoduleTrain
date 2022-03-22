package com.repositories.impl;

import com.entities.NetflixPlanEntity;
import com.entities.UserNetflixEntity;
import com.models.NetflixPlan;
import com.repositories.NetflixPlanEntityRepository;
import com.repositories.NetflixPlanModelRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class NetflixPlanModelRepositoryImpl implements NetflixPlanModelRepository {

    private final NetflixPlanEntityRepository repository;

    public NetflixPlanModelRepositoryImpl( NetflixPlanEntityRepository repository ) {
        this.repository = repository;
    }

    @Override
    public NetflixPlan save( NetflixPlan netflixPlan ) {
        return mapNetflixPlanEntityToModel( repository.save( mapNetflixPlanModelToEntity( netflixPlan ) ) );
    }

    @Override
    public Optional<NetflixPlan> findById( Integer id ) {
        AtomicReference<NetflixPlanEntity> netflixPlanEntity = new AtomicReference<>(null);
        repository.findById( id ).ifPresent( (plan) -> { netflixPlanEntity.set( plan ); } );
        return Optional.ofNullable( mapNetflixPlanEntityToModel( netflixPlanEntity.get() ) );
    }

    @Override
    public Optional<NetflixPlan> findByName( String name ) {
        AtomicReference<NetflixPlanEntity> netflixPlanEntity = new AtomicReference<>(null);
        repository.findByPlanName( name ).ifPresent( (plan) -> { netflixPlanEntity.set( plan ); } );
        return Optional.ofNullable( mapNetflixPlanEntityToModel( netflixPlanEntity.get() ) );
    }

    @Override
    public List<NetflixPlan> findAll() {
        return mapListNetflixPlanoEntityToModel( repository.findAll() );
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById( id );
    }

    private NetflixPlanEntity mapNetflixPlanModelToEntity( NetflixPlan netflixPlan ) {
        if ( netflixPlan == null ) return null;
        return NetflixPlanEntity.builder()
                .id( netflixPlan.getId() )
                .planName( netflixPlan.getPlanName() )
                .value( netflixPlan.getValue() )
                .build();
    }

    private NetflixPlan mapNetflixPlanEntityToModel( NetflixPlanEntity netflixPlanEntity ) {
        if ( netflixPlanEntity == null ) return null;
        return NetflixPlan.builder()
                .id( netflixPlanEntity.getId() )
                .planName( netflixPlanEntity.getPlanName() )
                .value( netflixPlanEntity.getValue() )
                .build();
    }

    private List<NetflixPlan> mapListNetflixPlanoEntityToModel( List<NetflixPlanEntity> netflixPlanEntities ) {
        List<NetflixPlan> netflixPlans = new ArrayList<>();
        for( NetflixPlanEntity netflixPlanEntity : netflixPlanEntities ) {
            NetflixPlan netflixPlan = NetflixPlan.builder()
                    .id( netflixPlanEntity.getId() )
                    .planName( netflixPlanEntity.getPlanName() )
                    .value( netflixPlanEntity.getValue() )
                    .build();
            netflixPlans.add( netflixPlan );
        }
        return netflixPlans;
    }
}
