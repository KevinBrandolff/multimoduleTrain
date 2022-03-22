package com.controllers;

import com.dtos.ProfileDTO;
import com.dtos.ServiceAccountsDTO;
import com.dtos.UserNetflixDTO;
import com.models.Profile;
import com.models.ServiceAccounts;
import com.models.UserNetflix;
import com.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController("kevinProfileController")
@RequestMapping( value = "/profile" )
public class ProfileController {

    private final ProfileService service;

    public ProfileController( ProfileService profileService ) {
        this.service = profileService;
    }

    @PostMapping
    public ResponseEntity< ProfileDTO > createProfile( @Valid @RequestBody ProfileDTO profile ){
        return new ResponseEntity<>( mapProfileModelToDTO( service.createProfile( mapProfileDTOtoModel(profile) ) ), HttpStatus.CREATED );
    }

    @PutMapping
    public ResponseEntity< ProfileDTO > updateProfile( @Valid @RequestBody ProfileDTO profile ){
        return ResponseEntity.ok( mapProfileModelToDTO( service.updateProfile( mapProfileDTOtoModel(profile) ) ) );
    }

    @GetMapping( value = "/{id}")
    public ResponseEntity< ProfileDTO > findById( @PathVariable Integer id ){
        return ResponseEntity.ok( mapProfileModelToDTO( service.findById( id ) ) );
    }

    @GetMapping
    public ResponseEntity< List<ProfileDTO> > findAll(){
        return ResponseEntity.ok( mapListProfileModelToDTO( service.findAll() ) );
    }

    @DeleteMapping( value = "/{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteById( Integer id ){
        service.deleteById( id );
    }

    @PostMapping( value = "/{profileId}/service/netflix")
    public ResponseEntity< ProfileDTO > addNetflixService( @RequestBody UserNetflixDTO userNetflixDTO, @PathVariable Integer profileId ){
        return ResponseEntity.ok( mapProfileModelToDTO( service.addNetflixService( mapUserNetflixDTOtoModel( userNetflixDTO ), profileId ) ) );
    }

    private Profile mapProfileDTOtoModel( ProfileDTO profileDTO ) {
        return Profile.builder()
                .id( profileDTO.getId() )
                .name( profileDTO.getName() )
                .age( profileDTO.getAge() )
                .interests( profileDTO.getInterests() )
                .createAt( profileDTO.getCreateAt() )
                .updatedAt( profileDTO.getUpdatedAt() )
                .build();
    }

    private ProfileDTO mapProfileModelToDTO( Profile profile ) {
        return ProfileDTO.builder()
                .id( profile.getId() )
                .name( profile.getName() )
                .age( profile.getAge() )
                .interests( profile.getInterests() )
                .createAt( profile.getCreateAt() )
                .updatedAt( profile.getUpdatedAt() )
                .serviceAccountsDTO( mapServiceAccountsModelToDTO( profile.getServiceAccounts() ) )
                .build();
    }

    private List<ProfileDTO> mapListProfileModelToDTO( List<Profile> profiles ) {
        List<ProfileDTO> profileDTOs = new ArrayList<>();
        for ( Profile profile : profiles ) {
            ProfileDTO profileDTO = ProfileDTO.builder()
                    .id( profile.getId() )
                    .name( profile.getName() )
                    .age( profile.getAge() )
                    .interests( profile.getInterests() )
                    .createAt( profile.getCreateAt() )
                    .updatedAt( profile.getUpdatedAt() )
                    .serviceAccountsDTO( mapServiceAccountsModelToDTO( profile.getServiceAccounts() ) )
                    .build();
            profileDTOs.add( profileDTO );
        }
        return profileDTOs;
    }

    private UserNetflix mapUserNetflixDTOtoModel(UserNetflixDTO userNetflixDTO ) {
        return UserNetflix.builder()
                .username( userNetflixDTO.getUsername() )
                .password( userNetflixDTO.getPassword() )
                .planName( userNetflixDTO.getPlanName() )
                .build();
    }

    private List<ServiceAccountsDTO> mapServiceAccountsModelToDTO(List<ServiceAccounts> serviceAccounts) {
        if ( serviceAccounts == null ) return null;

        List<ServiceAccountsDTO> servicesDTO = new ArrayList<>();
        for ( ServiceAccounts service : serviceAccounts ) {
            ServiceAccountsDTO serviceDTO = ServiceAccountsDTO.builder()
                    .service( service.getService() )
                    .plan( service.getPlan() )
                    .build();
            servicesDTO.add( serviceDTO );
        }
        return servicesDTO;
    }

}
