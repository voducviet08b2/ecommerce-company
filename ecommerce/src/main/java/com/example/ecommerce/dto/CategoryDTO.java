package com.example.ecommerce.dto;

public class CategoryDTO {
    private int id;
    private String categoryName;

    public CategoryDTO(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryDTO() {
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }


}
