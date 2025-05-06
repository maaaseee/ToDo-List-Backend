package com.aieta.springboot.todo_app.application.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {
    
    @NotBlank(message = "El nombre no puede estar vacio.")
    @Size(min = 3, max = 120, message = "El nombre debe contener entre 3 y 120 caracteres.")
    private String name;

    @NotBlank(message = "El color no puede estar vacio")
    @Pattern(
        regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", 
        message = "El color debe cumplir con el formato de color hexadecimal"
        )
    private String hexColor;

    @NotBlank(message = "El ID de usuario no puede estar vacio.")
    private String userId;

    public CreateCategoryRequest(String name, String hexColor, String userId) {
        this.name = name;
        this.hexColor = hexColor;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHexColor() {
        return hexColor;
    }
    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
