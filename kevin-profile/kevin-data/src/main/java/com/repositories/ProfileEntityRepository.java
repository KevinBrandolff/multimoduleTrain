package com.repositories;

import com.entities.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository( value = "kevinProfileRepository" )
public interface ProfileEntityRepository extends CrudRepository<ProfileEntity, Integer> {

    List<ProfileEntity> findAll();

}
