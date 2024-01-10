package org.voiture.venteoccaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class VenteOccazApplication {
    public static void main(String[] args) {
        SpringApplication.run(VenteOccazApplication.class, args);
    }
}
