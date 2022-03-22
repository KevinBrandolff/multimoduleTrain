package com.repositoriesTest;

import com.appContext.AppTest;
import com.entities.NetflixPlanEntity;
import com.repositories.NetflixPlanEntityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {AppTest.class})
@ActiveProfiles("kevin")
public class NetflixPlanEntityRepositoryTest {

    @Autowired
    private NetflixPlanEntityRepository repository;

    private static NetflixPlanEntity netflixPlanEntity;

    @BeforeAll
    static void createEntity(){
        netflixPlanEntity = NetflixPlanEntity.builder()
                .planName("FAMILY")
                .value(30.0)
                .build();
    }

    @Test
    void save(){
        NetflixPlanEntity netflixPlanEntitySaved = repository.save( netflixPlanEntity );
        assertNotNull( netflixPlanEntitySaved.getId() );
    }

    @Test
    void update(){
        NetflixPlanEntity netflixPlanEntitySaved = repository.save( netflixPlanEntity );
        NetflixPlanEntity netflixPlanEntityToUpdate = NetflixPlanEntity.builder()
                .id( netflixPlanEntitySaved.getId() )
                .planName("test update")
                .value(40.0)
                .build();
        NetflixPlanEntity netflixPlanEntityUpdated = repository.save( netflixPlanEntityToUpdate );
        assertTrue( repository.findById(netflixPlanEntitySaved.getId()).get().getPlanName().equals("test update") );
        assertSame(netflixPlanEntitySaved.getId(), netflixPlanEntityUpdated.getId());
    }

    @Test
    void findById(){
        NetflixPlanEntity netflixPlanEntitySaved = repository.save( netflixPlanEntity );
        assertTrue( repository.findById( netflixPlanEntitySaved.getId() ).isPresent() );
    }

    @Test
    void findAll(){
        repository.save( netflixPlanEntity );
        assertFalse( repository.findAll().isEmpty() );
    }

    @Test
    void findByPlanName(){
        NetflixPlanEntity netflixPlanEntitySaved = repository.save( netflixPlanEntity );
        assertTrue( repository.findByPlanName( netflixPlanEntitySaved.getPlanName() ).isPresent() );
    }

    @Test
    void deleteById(){
        NetflixPlanEntity netflixPlanEntitySaved = repository.save( netflixPlanEntity );
        repository.deleteById( netflixPlanEntitySaved.getId() );
        assertFalse( repository.findById( netflixPlanEntitySaved.getId() ).isPresent() );
    }

}
