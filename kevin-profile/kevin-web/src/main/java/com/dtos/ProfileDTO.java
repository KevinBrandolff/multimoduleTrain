package com.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Integer id;
    @NotNull( message = "Name required" )
    private String name;
    private Integer age;
    private String interests;
    private LocalDate createAt;
    private LocalDate updatedAt;
    private List<ServiceAccountsDTO> serviceAccountsDTO;

}
