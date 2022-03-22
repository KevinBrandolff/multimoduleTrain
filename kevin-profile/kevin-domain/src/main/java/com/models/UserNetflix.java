package com.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNetflix {

    private Integer id;
    private String username;
    private String password;
    private String planName;

}
