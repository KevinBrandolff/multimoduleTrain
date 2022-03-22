package com.repositories;

import com.entities.UserNetflixEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository( "kevinUserNetflixEntityRepository" )
public interface UserNetflixEntityRepository extends CrudRepository<UserNetflixEntity, Integer> {

    Optional<UserNetflixEntity> findByUsername(String username );

}
