package com.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAccountsDTO {

    private String service;
    private String plan;

}
