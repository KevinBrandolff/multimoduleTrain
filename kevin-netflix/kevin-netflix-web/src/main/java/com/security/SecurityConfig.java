package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpMethod.*;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authentication;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.headers().frameOptions().sameOrigin().and().cors().and()
                .exceptionHandling().authenticationEntryPoint( new CustomAuthenticationEntryPoint() ).and()
                .csrf().disable().authorizeRequests()
                .antMatchers( POST, "/login" ).permitAll()
                .antMatchers( POST, "/userNetflix/" ).permitAll()
                .antMatchers( POST, "/netflixPlan/" ).permitAll()
                .antMatchers( POST, "/userNetflix/user/{username}/plan/{planName}" )
                .hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .addFilterBefore( new JWTLoginFilter( "/login", authenticationManager() ),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore( new JWTAuthenticationFilter(authentication, new CustomAccessDeniedHandler()), UsernamePasswordAuthenticationFilter.class );
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(authentication).passwordEncoder(new BCryptPasswordEncoder());
    }


}
