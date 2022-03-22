package com.services;

import com.models.NetflixPlan;

public interface NetflixPlanService {

    NetflixPlan createNetflixPlan( NetflixPlan netflixPlan );
    NetflixPlan updateNetflixPlan( NetflixPlan netflixPlan );
    NetflixPlan findById( Integer id );
    void deleteById( Integer id );

}
