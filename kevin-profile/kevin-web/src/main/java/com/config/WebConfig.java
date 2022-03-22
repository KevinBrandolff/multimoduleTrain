package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DomainConfig.class, DataConfig.class})
@ComponentScan("com.controllers")
public class WebConfig {
}
