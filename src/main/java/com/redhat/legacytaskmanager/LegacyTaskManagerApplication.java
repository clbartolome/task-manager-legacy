package com.redhat.legacytaskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LegacyTaskManagerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LegacyTaskManagerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LegacyTaskManagerApplication.class, args);
    }
}