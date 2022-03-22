package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Configuration
@ComponentScan( basePackages = {"com.services", "com.repositories"} )
public class DomainConfig {
}
