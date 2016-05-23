package com.sainsburys.webextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point to run the SpringBoot console application.
 */
@SpringBootApplication
public class Application {

    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebEnvironment(false);
        app.run(args);
    }
}
