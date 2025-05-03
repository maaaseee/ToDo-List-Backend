package com.aieta.springboot.todo_app.application.dto.category;

public class UpdateCategoryRequest {
    private String name;
    private String hexColor;

    public UpdateCategoryRequest(String name, String hexColor) {
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
