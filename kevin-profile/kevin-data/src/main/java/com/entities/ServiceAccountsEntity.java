package com.entities;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "kevinServiceAccounts" )
@Table( name = "service_accounts_kevin" )
public class ServiceAccountsEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    private String service;

    private String plan;

    private String username;

    private String password;

    private Integer idUserNetflix;

    @ManyToOne
    @JoinColumn( name = "id_profile" )
    private ProfileEntity profile;

}
