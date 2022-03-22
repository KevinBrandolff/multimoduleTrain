package com.models;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    private Integer id;
    private String name;
    private Integer age;
    private String interests;
    private LocalDate createAt;
    private LocalDate updatedAt;
    private List<ServiceAccounts> serviceAccounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(name, profile.name) && Objects.equals(age, profile.age) && Objects.equals(interests, profile.interests) && Objects.equals(createAt, profile.createAt) && Objects.equals(updatedAt, profile.updatedAt);
    }

}
