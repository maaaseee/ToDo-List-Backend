package com.aieta.springboot.todo_app.application.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {
    
    @NotBlank
    @Size(max = 120)
    private String name;

    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
    private String hexColor;

    @NotBlank
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
