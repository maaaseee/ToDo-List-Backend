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


    public CreateCategoryRequest(String name, String hexColor) {
        this.name = name;
        this.hexColor = hexColor;
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
}
