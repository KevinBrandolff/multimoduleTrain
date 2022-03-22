package com.appContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DataConfigTest.class})
public class AppTest {

    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }

}
