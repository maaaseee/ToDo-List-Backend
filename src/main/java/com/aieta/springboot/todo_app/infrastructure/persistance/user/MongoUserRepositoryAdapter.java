package com.aieta.springboot.todo_app.infrastructure.persistance.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aieta.springboot.todo_app.domain.model.user.User;
import com.aieta.springboot.todo_app.domain.repository.UserRepository;

@Repository
public class MongoUserRepositoryAdapter implements UserRepository {

    private final MongoUserRepository userRepository;

    public MongoUserRepositoryAdapter(MongoUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
