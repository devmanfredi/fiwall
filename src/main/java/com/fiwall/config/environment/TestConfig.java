package com.fiwall.config.environment;

import com.fiwall.service.DBService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    DBService dbService;

    public TestConfig(DBService dbService) {
        this.dbService = dbService;
    }

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() {

        if (!"create".equals(strategy)) return false;

        dbService.instantiateTestDatabase();

        return true;
    }
}
