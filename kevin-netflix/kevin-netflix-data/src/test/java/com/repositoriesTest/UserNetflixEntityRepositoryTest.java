package com.repositoriesTest;

import com.appContext.AppTest;
import com.entities.UserNetflixEntity;
import com.repositories.UserNetflixEntityRepository;
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
public class UserNetflixEntityRepositoryTest {

    @Autowired
    private UserNetflixEntityRepository repository;

    private static UserNetflixEntity userNetflixEntity;

    @BeforeAll
    static void createEntity(){
        userNetflixEntity = UserNetflixEntity.builder()
                .username("test")
                .password("test123")
                .role( UserNetflixEntity.RoleEnum.USER )
                .build();
    }

    @Test
    void save(){
        UserNetflixEntity userNetflixEntitySaved = repository.save( userNetflixEntity );
        assertNotNull( userNetflixEntitySaved.getId() );
    }

    @Test
    void update(){
        UserNetflixEntity userNetflixEntitySaved = repository.save( userNetflixEntity );
        UserNetflixEntity userNetflixEntityToUpdate = UserNetflixEntity.builder()
                .id( userNetflixEntitySaved.getId() )
                .username("test update")
                .password("test update")
                .role( UserNetflixEntity.RoleEnum.ADMIN )
                .build();
        UserNetflixEntity userNetflixEntityUpdated = repository.save( userNetflixEntityToUpdate );
        assertTrue( repository.findById(userNetflixEntitySaved.getId()).get().getUsername().equals("test update") );
        assertSame(userNetflixEntitySaved.getId(), userNetflixEntityUpdated.getId());
    }

    @Test
    void findById(){
        UserNetflixEntity userNetflixEntitySaved = repository.save( userNetflixEntity );
        assertTrue( repository.findById( userNetflixEntitySaved.getId() ).isPresent() );
    }

    @Test
    void findByUsername(){
        UserNetflixEntity userNetflixEntitySaved = repository.save( userNetflixEntity );
        assertTrue( repository.findByUsername( userNetflixEntitySaved.getUsername() ).isPresent() );
    }

    @Test
    void deleteById(){
        UserNetflixEntity userNetflixEntitySaved = repository.save( userNetflixEntity );
        repository.deleteById( userNetflixEntitySaved.getId() );
        assertFalse( repository.findById( userNetflixEntitySaved.getId() ).isPresent() );
    }

}
