package com.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.entities")
@EnableJpaRepositories("com.repositories")
@PropertySource(value = "classpath:kevin-netflix-data.yml", factory = YamlPropertySourceFactory.class)
public class DataConfig {
}
