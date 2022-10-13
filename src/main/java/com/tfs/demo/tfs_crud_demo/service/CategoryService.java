package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();

    public Category getCategoryById(String id);

    public void saveCategory(Category theCategory);

    public void disableCategory(String id);

    public boolean CheckDuplicateId(String categoryId);
}
