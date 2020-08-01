package com.kavara.myapplication;
class CategoryModel {
    private String categoryName;

    public CategoryModel() {
    }

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
