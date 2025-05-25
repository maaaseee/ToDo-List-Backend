package com.aieta.springboot.todo_app.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aieta.springboot.todo_app.config.exception.UserNotFoundException;
import com.aieta.springboot.todo_app.domain.model.user.User;
import com.aieta.springboot.todo_app.domain.repository.UserRepository;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("Email: '%s' existe en el sistema.", email)));

        return user;
    }
}
