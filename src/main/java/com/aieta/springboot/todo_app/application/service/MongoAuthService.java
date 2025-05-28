package com.aieta.springboot.todo_app.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aieta.springboot.todo_app.application.dto.user.AuthRequest;
import com.aieta.springboot.todo_app.application.dto.user.AuthResponse;
import com.aieta.springboot.todo_app.application.dto.user.RegisterRequest;
import com.aieta.springboot.todo_app.config.exception.EmailAlreadyOnUseException;
import com.aieta.springboot.todo_app.config.security.JwtUtils;
import com.aieta.springboot.todo_app.domain.model.user.User;
import com.aieta.springboot.todo_app.domain.repository.UserRepository;

@Service
public class MongoAuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyOnUseException("El correo ya esta en uso");
        }

        User user = new User(
            request.getName(), 
            request.getEmail(), 
            passwordEncoder.encode(request.getPassword())
        );
        
        userRepository.save(user);

        String token = jwtUtils.generateToken(user);
        
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        // Autenticar usuario
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        // Obtener usuario autenticado
        User user = (User) authentication.getPrincipal();

        // Generar token
        String token = jwtUtils.generateToken(user);
        
        return new AuthResponse(token, user.getName(), user.getEmail());
    }
}
