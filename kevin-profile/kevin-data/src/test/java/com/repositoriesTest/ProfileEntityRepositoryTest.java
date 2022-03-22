package com.repositoriesTest;

import com.appContext.AppTest;
import com.entities.ProfileEntity;
import com.repositories.ProfileEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ContextConfiguration(classes = {AppTest.class})
@ActiveProfiles("kevin")
public class ProfileEntityRepositoryTest {

    @Autowired
    private ProfileEntityRepository repository;

    private static ProfileEntity profileEntity;

    @BeforeAll
    public static void createProfileEntity(){
        profileEntity = ProfileEntity.builder()
                .name("kevin")
                .age(21)
                .interests("surf")
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    public void saveProfileEntity(){
        ProfileEntity profileEntitySaved = repository.save( profileEntity );

        Assertions.assertTrue( profileEntitySaved.getId() != null );
    }

    @Test
    public void updateProfileEntity(){}

    @Test
    public void findProfileEntityById(){
        ProfileEntity profileEntitySaved = repository.save( profileEntity );

        Assertions.assertTrue( repository.findById(profileEntitySaved.getId()) != null );
    }

    @Test
    public void findAllProfilesEntity(){
        repository.save( profileEntity );

        Assertions.assertTrue( repository.findAll().get(0) != null );
    }

    @Test
    public void deleteProfileEntityById(){
        ProfileEntity profileEntitySaved = repository.save( profileEntity );

        repository.deleteById( profileEntitySaved.getId() );

        Assertions.assertFalse( repository.findById( profileEntitySaved.getId() ).isPresent() );
    }

}
