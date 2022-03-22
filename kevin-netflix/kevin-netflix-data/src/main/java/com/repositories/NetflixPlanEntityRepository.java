package com.repositories;

import com.entities.NetflixPlanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository( "kevinNetflixPlanEntityRepository" )
public interface NetflixPlanEntityRepository extends CrudRepository<NetflixPlanEntity, Integer> {

    List<NetflixPlanEntity> findAll();
    Optional<NetflixPlanEntity> findByPlanName(String name );

}
