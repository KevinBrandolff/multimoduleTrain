package com.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "kevinProfileEntity" )
@Table( name = "profile_kevin" )
public class ProfileEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column( nullable = false )
    private String name;

    private Integer age;

    private String interests;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToMany( mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<ServiceAccountsEntity> serviceAndCredentials;

}
