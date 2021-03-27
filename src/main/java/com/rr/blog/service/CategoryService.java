package com.rr.blog.service;

import com.rr.blog.entity.Category;

import java.util.List;

public interface CategoryService {
    Integer countCaterory();
    List<Category> listCategory();
    List<Category> listCategoryWithCount();
    Category getCategoryById(Integer id);
    void insertCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Integer id);
    Category getCategoryByName(String name);
    Integer countCategory();
}
