package com.controllers;

import com.dtos.NetflixPlanDTO;
import com.models.NetflixPlan;
import com.services.NetflixPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("kevinNetflixPlanController")
@RequestMapping( value = "/netflixPlan")
public class NetflixPlanController {

    private final NetflixPlanService service;

    public NetflixPlanController(NetflixPlanService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NetflixPlanDTO> createNetflixPlan(@RequestBody @Valid NetflixPlanDTO netflixPlanDTO ) {
        return new ResponseEntity<>( mapNetflixPlanModelToDTO( service.createNetflixPlan( mapNetflixPlanDTOtoModel( netflixPlanDTO ) ) ), HttpStatus.CREATED );
    }

    @PutMapping
    public ResponseEntity<NetflixPlanDTO> updateNetflixPlan( @RequestBody @Valid NetflixPlanDTO netflixPlanDTO ) {
        return ResponseEntity.ok( mapNetflixPlanModelToDTO( service.updateNetflixPlan( mapNetflixPlanDTOtoModel( netflixPlanDTO ) ) ) );
    }

    @GetMapping( value = "/{id}")
    public ResponseEntity<NetflixPlanDTO> findById( @PathVariable Integer id ) {
        return ResponseEntity.ok( mapNetflixPlanModelToDTO( service.findById( id ) ) );
    }

    @DeleteMapping( value = "/{id}" )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteById( @PathVariable Integer id ) {
        service.deleteById( id );
    }

    private NetflixPlan mapNetflixPlanDTOtoModel(NetflixPlanDTO netflixPlanDTO ) {
        return NetflixPlan.builder()
                .id( netflixPlanDTO.getId() )
                .planName( netflixPlanDTO.getPlanName() )
                .value( netflixPlanDTO.getValue() )
                .build();
    }

    private NetflixPlanDTO mapNetflixPlanModelToDTO( NetflixPlan userNetflix ) {
        return NetflixPlanDTO.builder()
                .id( userNetflix.getId() )
                .planName( userNetflix.getPlanName() )
                .value( userNetflix.getValue() )
                .build();
    }

}
