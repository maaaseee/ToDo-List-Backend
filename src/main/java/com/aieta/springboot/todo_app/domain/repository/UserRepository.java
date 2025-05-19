package com.aieta.springboot.todo_app.domain.repository;

import java.util.Optional;

import com.aieta.springboot.todo_app.domain.model.user.User;

public interface UserRepository {
    Optional<User> findById(String userId);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteById(String userId);
    boolean existsByEmail(String email);
}
