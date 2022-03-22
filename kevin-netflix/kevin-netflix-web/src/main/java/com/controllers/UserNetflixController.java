package com.controllers;

import com.dtos.UserNetflixDTO;
import com.models.UserNetflix;
import com.services.UserNetflixService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController( "userNetflixService" )
@RequestMapping( value = "/userNetflix" )
public class UserNetflixController {

    private final UserNetflixService service;

    public UserNetflixController(UserNetflixService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserNetflixDTO> createUserNetflix( @RequestBody @Valid UserNetflixDTO userNetflixDTO ) {
        return new ResponseEntity<>( mapUserNetflixModelToDTO( service.createUserNetflix( mapUserNetflixDTOtoModel( userNetflixDTO ) ) ), HttpStatus.CREATED );
    }

    @PutMapping
    public ResponseEntity<UserNetflixDTO> updateUserNetflix( @RequestBody @Valid UserNetflixDTO userNetflixDTO ) {
        return ResponseEntity.ok( mapUserNetflixModelToDTO( service.updateUserNetflix( mapUserNetflixDTOtoModel( userNetflixDTO ) ) ) );
    }

    @GetMapping( value = "/{id}")
    public ResponseEntity<UserNetflixDTO> findById( @PathVariable Integer id ) {
        return ResponseEntity.ok( mapUserNetflixModelToDTO( service.findById( id ) ) );
    }

    @DeleteMapping( value = "/{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteById( @PathVariable Integer id ) {
        service.deleteById( id );
    }

    @PostMapping( value = "/plan/{planName}" )
    public ResponseEntity<UserNetflixDTO> addPlanToUser(@PathVariable String planName, Principal username) {
        return ResponseEntity.ok( mapUserNetflixModelToDTO( service.addPlanToUser( username.getName(), planName ) ) );
    }

    @PostMapping( value = "/user/{username}/plan/{planName}" )
    public ResponseEntity<UserNetflixDTO> adminAddPlanToUser( @PathVariable String username, @PathVariable String planName ) {
        return ResponseEntity.ok( mapUserNetflixModelToDTO( service.addPlanToUser( username, planName ) ) );
    }

    private UserNetflix mapUserNetflixDTOtoModel( UserNetflixDTO userNetflixDTO ) {
        return UserNetflix.builder()
                .id( userNetflixDTO.getId() )
                .username( userNetflixDTO.getUsername() )
                .password( userNetflixDTO.getPassword() )
                .role( userNetflixDTO.getRole() )
                .netflixPlan( userNetflixDTO.getNetflixPlan() )
                .build();
    }

    private UserNetflixDTO mapUserNetflixModelToDTO( UserNetflix userNetflix ) {
        return UserNetflixDTO.builder()
                .id( userNetflix.getId() )
                .username( userNetflix.getUsername() )
                .password( userNetflix.getPassword() )
                .role( userNetflix.getRole() )
                .netflixPlan( userNetflix.getNetflixPlan() )
                .build();
    }
}
