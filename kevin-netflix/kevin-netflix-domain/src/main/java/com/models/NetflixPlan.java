package com.models;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NetflixPlan {

    private Integer id;
    private String planName;
    private Double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetflixPlan that = (NetflixPlan) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(planName, that.planName) &&
                Objects.equals(value, that.value);
    }
}
