package com.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserNetflixDTO {

    private String username;
    private String password;
    private String planName;

}
