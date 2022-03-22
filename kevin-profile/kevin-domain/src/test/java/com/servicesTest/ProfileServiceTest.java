package com.servicesTest;

import com.appContext.AppTest;
import com.exceptions.ResourceNotFoundException;
import com.models.Profile;
import com.repositories.ProfileModelRepository;
import com.services.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {AppTest.class})
@ActiveProfiles("kevin")
public class ProfileServiceTest {

    @Mock
    private ProfileModelRepository repository;

    @InjectMocks
    private ProfileServiceImpl service;

    private static Profile profileToSave, profileToUpdate, profileSaved, profileUpdated;

    @BeforeAll
    private static void createProfiles(){
        profileToSave = Profile.builder()
                .name("kevin")
                .age(21)
                .interests("surf")
                .build();

        profileSaved = Profile.builder()
                .id(1)
                .name("kevin")
                .age(21)
                .interests("surf")
                .createAt(LocalDate.now())
                .build();

        profileToUpdate = Profile.builder()
                .id(1)
                .name("kevin to update")
                .age(22)
                .interests("surf")
                .build();

        profileUpdated = Profile.builder()
                .id(1)
                .name("kevin to update")
                .age(22)
                .interests("surf")
                .createAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
    }

    @Test
    public void createProfileTest(){
        when( repository.save( any( Profile.class ) ) ).thenReturn( profileSaved );

        Profile profileToTest = service.createProfile( profileToSave );

        assertTrue( profileToTest.getId() == 1 );
    };

    @Test
    public void updateProfileTest(){

        when( repository.save( profileToUpdate ) ).thenReturn( profileUpdated );
        when( repository.findById( profileToUpdate.getId() ) ).thenReturn( Optional.of( profileSaved ) );

        Profile profileToTest = service.updateProfile( profileToUpdate );

        assertTrue( profileToTest.getName().equals( "kevin to update" ) );
    };

    @Test
    public void findByIdThrowResourceNotFoundException(){
        when( repository.findById( profileSaved.getId() ) ).thenReturn( Optional.ofNullable( null ) );

        assertThrows( ResourceNotFoundException.class,
                () -> service.findById( profileSaved.getId() ) );
    };


}
