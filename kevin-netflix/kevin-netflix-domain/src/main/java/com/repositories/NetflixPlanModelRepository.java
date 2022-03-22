package com.repositories;

import com.models.NetflixPlan;

import java.util.List;
import java.util.Optional;

public interface NetflixPlanModelRepository {

    NetflixPlan save( NetflixPlan netflixPlan );
    Optional<NetflixPlan> findById(Integer id );
    Optional<NetflixPlan> findByName( String name );
    List<NetflixPlan> findAll();
    void deleteById( Integer id );

}
