package com.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NetflixPlanDTO {

    private Integer id;
    @NotNull( message = "PlanName cant be null!" )
    private String planName;
    @NotNull( message = "Value cant bem null!")
    private Double value;

}
