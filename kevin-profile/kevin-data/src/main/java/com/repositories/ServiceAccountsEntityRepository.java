package com.repositories;

import com.entities.ServiceAccountsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAccountsEntityRepository extends CrudRepository<ServiceAccountsEntity, Integer> {
}
