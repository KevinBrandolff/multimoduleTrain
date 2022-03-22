package com.servicesTest;

import com.appContext.AppTest;
import com.exceptions.NetflixResourceNotFoundException;
import com.exceptions.userNetflixExceptions.UserNetflixPersistenceException;
import com.models.NetflixPlan;
import com.models.UserNetflix;
import com.repositories.NetflixPlanModelRepository;
import com.repositories.UserNetflixModelRepository;
import com.services.impl.UserNetflixServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {AppTest.class})
@ActiveProfiles("kevin")
public class UserNetflixServiceTest {

    @Mock
    private UserNetflixModelRepository repository;

    @Mock
    private NetflixPlanModelRepository planRepository;

    @InjectMocks
    private UserNetflixServiceImpl service;

    private static UserNetflix userNetflixToSave, userNetflixToUpdate, userNetflixSaved, userNetflixUpdated;

    @BeforeAll
    private static void createUsers(){
        userNetflixToSave = UserNetflix.builder()
                .username("test")
                .password("123456")
                .role("USER")
                .build();

        userNetflixSaved = UserNetflix.builder()
                .id(1)
                .username("test")
                .password("123456")
                .role("USER")
                .build();

        userNetflixToUpdate = UserNetflix.builder()
                .id(1)
                .username("test update")
                .password("123456 update")
                .role("USER")
                .build();

        userNetflixUpdated = UserNetflix.builder()
                .id(1)
                .username("test update")
                .password("123456 update")
                .role("USER")
                .build();
    }

    @Test
    void createUserNetflix(){
        when( repository.save( userNetflixToSave ) ).thenReturn( userNetflixSaved );

        UserNetflix userNetflixSaved = service.createUserNetflix( userNetflixToSave );

        assertNotNull( userNetflixSaved.getId() );
    }

    @Test
    void createUserNetflixWithANameExistentThrowUserNetflixPersistenceException(){
        when( repository.findByUsername( userNetflixSaved.getUsername() ) ).thenReturn( Optional.of( userNetflixSaved ) );

        assertThrows( UserNetflixPersistenceException.class, () -> service.createUserNetflix( userNetflixToSave ));
    }

    @Test
    void updateUserNetflix(){
        when( repository.findById( userNetflixSaved.getId() ) ).thenReturn( Optional.of( userNetflixSaved ) );
        when( repository.save( userNetflixToUpdate ) ).thenReturn( userNetflixUpdated );

        UserNetflix userNetflixUpdated = service.updateUserNetflix( userNetflixToUpdate );

        assertEquals(this.userNetflixUpdated.getUsername(), userNetflixUpdated.getUsername());
    }

    @Test
    void updateUserNetflixWithAIdExistentThrowNetflixResourceNotFoundException(){
        when( repository.findById( userNetflixSaved.getId() ) ).thenReturn( Optional.ofNullable( null ) );

        assertThrows( NetflixResourceNotFoundException.class, () -> service.updateUserNetflix( userNetflixToSave ));
    }

    @Test
    void findById(){
        when( repository.findById( userNetflixSaved.getId() ) ).thenReturn( Optional.ofNullable( userNetflixSaved ) );

        assertNotNull( repository.findById( userNetflixSaved.getId() ) );
    }

    @Test
    void addPlanToUser(){
        NetflixPlan netflixPlan = NetflixPlan.builder().id(1).planName("FAMILY").value(30.0).build();

        UserNetflix userNetflix = UserNetflix.builder().build();
        BeanUtils.copyProperties( userNetflixSaved, userNetflix );

        userNetflix.setNetflixPlan( netflixPlan );

        when( repository.findByUsername( userNetflixSaved.getUsername() ) )
                .thenReturn( Optional.of( userNetflixSaved ) );
        when( planRepository.findByName( netflixPlan.getPlanName() ) )
                .thenReturn( Optional.of( netflixPlan ) );
        when( repository.save( any( UserNetflix.class ) ) ).thenReturn( userNetflix );

        UserNetflix userNetflixResponse = service.addPlanToUser( userNetflixSaved.getUsername(), netflixPlan.getPlanName() );

        assertEquals(userNetflixResponse.getNetflixPlan(), netflixPlan);
        assertNotNull( userNetflixResponse.getId() );

    }

}
