package com.dtos;

import com.models.NetflixPlan;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNetflixDTO {

    private Integer id;
    @NotNull( message = "Username cant be null!" )
    private String username;
    @NotNull( message = "Password cant be null!" )
    private String password;
    private NetflixPlan netflixPlan;
    private String role;

}
