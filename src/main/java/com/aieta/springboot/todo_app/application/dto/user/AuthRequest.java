package com.aieta.springboot.todo_app.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no tiene el formato correcto")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email;}

    public void setEmail(String email) {this.email = email;}
    
    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
