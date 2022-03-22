package com.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "kevinUserNetflixEntity" )
@Table( name = "user_netflix_kevin" )
public class UserNetflixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn( name = "id_netflix_plan" )
    private NetflixPlanEntity netflixPlan;

    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    private RoleEnum role;

    public static enum RoleEnum {
        ADMIN, USER;
    }


}
