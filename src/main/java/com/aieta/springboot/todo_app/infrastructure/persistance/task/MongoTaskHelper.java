package com.aieta.springboot.todo_app.infrastructure.persistance.task;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.aieta.springboot.todo_app.domain.model.task.Task;

@Component
public class MongoTaskHelper {

    private final MongoTemplate mongoTemplate;

    public MongoTaskHelper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void clearCategoryReference(String categoryId) {
        Query query = new Query(Criteria.where("categoryId").is(categoryId));
        Update update = new Update().unset("categoryId");
        mongoTemplate.updateMulti(query, update, Task.class);
    }
}
