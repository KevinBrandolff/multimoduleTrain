package com.appContext;

import com.config.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.entities")
@EnableJpaRepositories("com.repositories")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class DataConfigTest {
}
