package com.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "kevinNetflixPlansEntity" )
@Table( name = "netflix_plan_kevin" )
public class NetflixPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false, unique = true )
    private String planName;

    @Column( nullable = false )
    private Double value;

}
