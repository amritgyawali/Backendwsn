package com.wsn.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test") // Exclude from tests
public class FlywayRepairConfig {

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            // First repair any failed migrations
            flyway.repair();
            
            // Then validate the migrations
            try {
                flyway.validate();
            } catch (Exception e) {
                // If validation fails, run repair and try again
                flyway.repair();
            }
            
            // Finally, run migrations
            flyway.migrate();
        };
    }
}
