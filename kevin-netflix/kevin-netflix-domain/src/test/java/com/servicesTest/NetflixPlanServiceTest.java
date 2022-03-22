package com.servicesTest;

import com.appContext.AppTest;
import com.exceptions.NetflixResourceNotFoundException;
import com.exceptions.netflixPlanExceptions.NetflixPlanPersistenceException;
import com.models.NetflixPlan;
import com.repositories.NetflixPlanModelRepository;
import com.services.impl.NetflixPlanServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {AppTest.class})
@ActiveProfiles("kevin")
public class NetflixPlanServiceTest {

    @Mock
    private NetflixPlanModelRepository repository;

    @InjectMocks
    private NetflixPlanServiceImpl service;

    private static NetflixPlan netflixPlanToSave, netflixPlanToUpdate, netflixPlanSaved, netflixPlanUpdated;

    @BeforeAll
    private static void createPlans(){
        netflixPlanToSave = NetflixPlan.builder()
                .planName("FAMILY")
                .value(30.0)
                .build();

        netflixPlanSaved = NetflixPlan.builder()
                .id(1)
                .planName("FAMILY")
                .value(30.0)
                .build();

        netflixPlanToUpdate = NetflixPlan.builder()
                .id(1)
                .planName("NORMAL")
                .value(40.0)
                .build();

        netflixPlanUpdated = NetflixPlan.builder()
                .id(1)
                .planName("NORMAL")
                .value(40.0)
                .build();
    }

    @Test
    void createNetflixPlan(){
        when( repository.save( netflixPlanToSave ) ).thenReturn( netflixPlanSaved );

        NetflixPlan netflixPlanSaved = service.createNetflixPlan( netflixPlanToSave );

        assertNotNull( netflixPlanSaved.getId() );
    }

    @Test
    void createNetflixPlanWithValueLessThanZeroThrowNetflixPlanPersistenceException(){
        NetflixPlan netflixPlanToSaveWithWrongValue = NetflixPlan.builder()
                .planName("test")
                .value(-3.0)
                .build();

        assertThrows( NetflixPlanPersistenceException.class, () ->
                service.createNetflixPlan(netflixPlanToSaveWithWrongValue) );
    }

    @Test
    void createNetflixPlanWithANameExistentThrowNetflixPlanPersistenceException(){
        when( repository.findByName( netflixPlanSaved.getPlanName() ) ).thenReturn( Optional.of( netflixPlanSaved ) );

        assertThrows( NetflixPlanPersistenceException.class, () ->
                service.createNetflixPlan(netflixPlanToSave) );
    }

    @Test
    void updateNetflixPlan(){
        when( repository.findById( netflixPlanSaved.getId() ) ).thenReturn( Optional.of( netflixPlanSaved ) );
        when( repository.save( netflixPlanToUpdate ) ).thenReturn( netflixPlanUpdated );

        NetflixPlan netflixPlanUpdated = service.updateNetflixPlan( netflixPlanToUpdate );

        assertEquals(this.netflixPlanUpdated.getPlanName(), netflixPlanUpdated.getPlanName());
    }

    @Test
    void updateNetflixPlanWithAIdNotExistentThrowNetflixResourceNotFoundException(){
        when( repository.findById( netflixPlanToUpdate.getId() ) ).thenReturn( Optional.ofNullable( null ) );

        assertThrows( NetflixResourceNotFoundException.class, () ->
                service.updateNetflixPlan( netflixPlanToUpdate ) );
    }

    @Test
    void findById(){
        when( repository.findById( 1 ) ).thenReturn( Optional.of( netflixPlanSaved ) );

        NetflixPlan netflixPlan = service.findById( 1 );

        assertNotNull( netflixPlan );
    }

    @Test
    void findByIdWithAIdNotExistentThrowNetflixResourceNotFoundException(){
        when( repository.findById( netflixPlanSaved.getId() ) ).thenReturn( Optional.ofNullable( null ) );

        assertThrows( NetflixResourceNotFoundException.class, () ->
                service.findById( netflixPlanSaved.getId() ) );
    }

}
