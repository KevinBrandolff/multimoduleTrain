package com.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceAccounts {

    private Integer id;
    private String service;
    private String plan;
    private String username;
    private String password;
    private Profile profile;
    private Integer idUserNetflix;

}
