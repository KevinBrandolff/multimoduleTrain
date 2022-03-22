package com.app;

import com.config.DomainConfig;
import com.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfig.class, DomainConfig.class})
public class AppNetflix {
    public static void main(String[] args) {
        SpringApplication.run(AppNetflix.class, args);
    }
}
