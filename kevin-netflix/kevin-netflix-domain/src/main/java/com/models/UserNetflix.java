package com.models;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNetflix {

    private Integer id;
    private String username;
    private String password;
    private NetflixPlan netflixPlan;
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNetflix that = (UserNetflix) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(netflixPlan, that.netflixPlan) && Objects.equals(role, that.role);
    }
}
