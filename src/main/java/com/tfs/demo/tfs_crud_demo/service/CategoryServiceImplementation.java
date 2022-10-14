package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.CategoryRepository;
import com.tfs.demo.tfs_crud_demo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository theCategoryRepository){
        categoryRepository = theCategoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        Optional<Category> result = categoryRepository.findById(id);

        Category theCategory = null;
        if(result.isPresent()){
            theCategory = result.get();
        }
        else {
            throw new RuntimeException("Category with id - " +id+ " not found!");
        }
        return theCategory;
    }

    @Override
    public void saveCategory(Category theCategory) {
        categoryRepository.save(theCategory);
    }

    @Override
    public void disableCategory(String id) {
        Optional<Category> result = categoryRepository.findById(id);

        Category theCategory = null;

        if(result.isPresent()){
            theCategory = result.get();
        } else {
            throw new RuntimeException("Category with id - " +id +" not found!");
        }
        theCategory.setStatus(false);
        categoryRepository.save(theCategory);
    }

    @Override
    public boolean CheckDuplicateId(String categoryId){
        Optional<Category> result = categoryRepository.findById(categoryId);

        Category theCategory = null;
        if(result.isPresent()){
            throw new RuntimeException("Category with id - " +categoryId+ " already exist, please try again!");
        }

        return true;
    }
}
