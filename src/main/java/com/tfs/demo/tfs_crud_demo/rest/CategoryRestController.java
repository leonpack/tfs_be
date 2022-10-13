package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Category;
import com.tfs.demo.tfs_crud_demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService theCategoryService){
        categoryService = theCategoryService;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public Category getCategoryById(@PathVariable String categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);

        if(theCategory == null){
            throw new RuntimeException("Category with id - " +categoryId + " not found!");
        }
        return theCategory;
    }

    @PostMapping("/categories")
    public String addCategory(@RequestBody Category theCategory){

        if(!categoryService.CheckDuplicateId(theCategory.getId())){
            return "Duplicate id issues has been found!";
        }
//        categoryService.saveCategory(theCategory);
//        return theCategory;
        categoryService.saveCategory(theCategory);
        return "Saved " + theCategory;
    }

    @PutMapping("/categories")
    public Category updateCategory(@RequestBody Category theCategory){
        categoryService.saveCategory(theCategory);
        return theCategory;
    }

    @DeleteMapping("/categories/{categoryId}")
    public String disableCategory(@PathVariable String categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);

        if(theCategory == null){
            throw new RuntimeException("Category with id - " +categoryId + " not found!!");
        }

        theCategory.setStatus(false);
        categoryService.saveCategory(theCategory);

        return "Disable category with id - " +categoryId + " completed!";
    }
}
