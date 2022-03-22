package com.services.impl;

import com.exceptions.NetflixModuleException;
import com.exceptions.ResourceNotFoundException;
import com.models.Profile;
import com.models.ServiceAccounts;
import com.models.User;
import com.models.UserNetflix;
import com.repositories.ProfileModelRepository;
import com.services.ManagementServiceAccounts;
import com.services.NetflixServiceConnector;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagementServiceAccountsImpl implements ManagementServiceAccounts {

    private static RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "http://localhost:8083/kevin";

    private static final String NETFLIX = "NETFLIX";

    private final ProfileModelRepository repository;
    private final NetflixServiceConnector netflixServiceConnector;

    public ManagementServiceAccountsImpl(ProfileModelRepository profileModelRepository, NetflixServiceConnector netflixServiceConnector) {
        this.repository = profileModelRepository;
        this.netflixServiceConnector = netflixServiceConnector;
    }

    @Override
    public Profile addNetflixService( UserNetflix userNetflix, Integer profileId ) {
        Profile profile = repository.findById( profileId ).orElseThrow( () -> new ResourceNotFoundException( profileId ) );;

        if ( verifyProfileHaveNetflixCredentials( profile ) ) {
            if ( verifyIfCredentialsAreDifferent( getNetflixServiceAccount(profile).get(), userNetflix ) ) {
                if ( loginToUpdateCredentialsOfServiceAccount( getNetflixServiceAccount(profile).get(), userNetflix ) ) {
                    updateServiceAccountsOfProfile( userNetflix, profile );
                    loginNetflixAndAddPlanNetflixService( userNetflix );
                }else {
                    ResponseEntity<UserNetflix> userNetflixRegisteredResponse = netflixServiceConnector.registerNetflixService( userNetflix );
                    userNetflix.setId( userNetflixRegisteredResponse.getBody().getId() );
                    updateServiceAccountsOfProfile( userNetflix, profile );
                    loginNetflixAndAddPlanNetflixService( userNetflix );
                }
            }else {
                loginNetflixAndAddPlanNetflixService( userNetflix );
            }
        }else {
            ResponseEntity<UserNetflix> userNetflixRegisteredResponse = netflixServiceConnector.registerNetflixService( userNetflix );
            userNetflix.setId( userNetflixRegisteredResponse.getBody().getId() );
            updateServiceAccountsOfProfile( userNetflix, profile );
            loginNetflixAndAddPlanNetflixService( userNetflix );
        }

        return profile;
    }

    private Boolean verifyProfileHaveNetflixCredentials( Profile profile ) {
        if ( profile.getServiceAccounts() == null ) return false;
        return getNetflixServiceAccount(profile).isPresent();
    }

    private Boolean verifyIfCredentialsAreDifferent(ServiceAccounts serviceAccountExistent, UserNetflix userNetflix ) {
        if ( !serviceAccountExistent.getUsername().equals( userNetflix.getUsername() ) ) return true;
        return !serviceAccountExistent.getPassword().equals(userNetflix.getPassword());
    }

    private Boolean loginToUpdateCredentialsOfServiceAccount( ServiceAccounts serviceAccountExistent, UserNetflix userNetflix ) {
        boolean isCredentialsUpdated = false;

        userNetflix.setId( serviceAccountExistent.getIdUserNetflix() );

        User user = User.builder().username( serviceAccountExistent.getUsername() )
                                  .password( serviceAccountExistent.getPassword() ).build();
        ResponseEntity<Object> loginResponse = netflixServiceConnector.loginNetflixService( user );

        if ( loginResponse.getStatusCode().equals( HttpStatus.OK ) ) {
            String token = loginResponse.getHeaders().getFirst("Authorization");
            ResponseEntity<UserNetflix> updateResponse = netflixServiceConnector.updateUserNetflixService( userNetflix, token );
            isCredentialsUpdated = updateResponse.getStatusCode().equals(HttpStatus.OK);
        }

        return isCredentialsUpdated;
    }

    private void loginNetflixAndAddPlanNetflixService( UserNetflix userNetflix ) {
        User user = User.builder().username( userNetflix.getUsername() )
                .password( userNetflix.getPassword() ).build();
        ResponseEntity<Object> loginResponse = netflixServiceConnector.loginNetflixService( user );

        if ( loginResponse.getStatusCode().equals( HttpStatus.OK ) ) {
            String token = loginResponse.getHeaders().getFirst( "Authorization" );
            ResponseEntity<UserNetflix> addPlanResponse = netflixServiceConnector.addNetflixPlanNetflixService( userNetflix, token );
            if ( !addPlanResponse.getStatusCode().equals( HttpStatus.OK ) ) {
                throw new NetflixModuleException("A problem occurred, couldn't add the plan!");
            }
        }else {
            throw new NetflixModuleException("A problem occurred, couldn't do the login!");
        }
    }

    private void updateServiceAccountsOfProfile( UserNetflix userNetflix, Profile profile ) {
        Optional<ServiceAccounts> serviceAccountOptional = getNetflixServiceAccount( profile );
        ServiceAccounts serviceAccount;
        if ( serviceAccountOptional.isPresent() ) {
            serviceAccountOptional.get().setUsername( userNetflix.getUsername() );
            serviceAccountOptional.get().setPassword( userNetflix.getPassword() );
            serviceAccountOptional.get().setProfile( profile );

            serviceAccount = serviceAccountOptional.get();
        }else {
            ServiceAccounts newServiceAccount = ServiceAccounts.builder()
                    .service( NETFLIX )
                    .plan( userNetflix.getPlanName() )
                    .username( userNetflix.getUsername() )
                    .password( userNetflix.getPassword() )
                    .idUserNetflix( userNetflix.getId() )
                    .profile( profile )
                    .build();

            serviceAccount = newServiceAccount;
        }

        List<ServiceAccounts> serviceAccountsListFiltered = profile.getServiceAccounts().stream()
                .filter( (account) -> !account.getService().equals( NETFLIX ) )
                .collect(Collectors.toList());

        serviceAccountsListFiltered.add( serviceAccount );

        profile.setServiceAccounts( serviceAccountsListFiltered );

        repository.save( profile );
    }

    private Optional<ServiceAccounts> getNetflixServiceAccount( Profile profile ) {
        return profile.getServiceAccounts().stream().findFirst()
                .filter( (account) -> account.getService().equals( NETFLIX ) );
    }

}
