package com.aieta.springboot.todo_app.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "todoappdb");
    }
}
