package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Category;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.CategoryService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CategoryRestController {

    private final CategoryService categoryService;

    private final FoodService foodService;

    @Autowired
    public CategoryRestController(CategoryService theCategoryService, FoodService theFoodService){
        categoryService = theCategoryService;
        foodService = theFoodService;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public Category getCategoryById(@PathVariable int categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);

        if(theCategory == null){
            throw new RuntimeException("Category with id - " +categoryId + " not found!");
        }
        return theCategory;
    }

    @PostMapping("/categories")
    public String addCategory(@RequestBody Category theCategory){
        categoryService.saveCategory(theCategory);
        return "Saved " + theCategory;
    }


    @PutMapping("/categories")
    public Category updateCategory(@RequestBody Category theCategory){
        Category existCategory = categoryService.getCategoryById(theCategory.getId());
        if(theCategory.getCategoryName()==null){
            theCategory.setCategoryName(existCategory.getCategoryName());
        }
        if(theCategory.getFoodList()==null){
            theCategory.setFoodList(existCategory.getFoodList());
        }
        if(theCategory.getStatus()==null){
            theCategory.setStatus(existCategory.getStatus());
        }
        theCategory.setFoodList(theCategory.getFoodList());
        for(Food item : theCategory.getFoodList()){
            Food addFood = foodService.getFoodById(item.getId());
            addFood.setTheCategory(theCategory);
        }
        categoryService.saveCategory(theCategory);
        return theCategory;
    }

    @DeleteMapping("/categories/{categoryId}")
    public String disableCategory(@PathVariable int categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);

        if(theCategory == null){
            throw new RuntimeException("Category with id - " +categoryId + " not found!!");
        }

        theCategory.setStatus(false);
        categoryService.saveCategory(theCategory);

        return "Disable category with id - " +categoryId + " completed!";
    }

    @PostMapping("/categories/{foodId}TO{categoryId}")
    public String addFoodToCategory(@PathVariable int categoryId,@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        Category theCategory = categoryService.getCategoryById(categoryId);
        if(theCategory == null || theFood == null){
            throw new RuntimeException("Category with id - " +categoryId+ " or food with id - " +foodId+ "not found!");
        }
        theCategory.addFood(theFood);
        theFood.setTheCategory(theCategory);
        categoryService.saveCategory(theCategory);
        foodService.saveFood(theFood);

        return "Add food: " +theFood.getFoodName() + " to category: " +theCategory.getCategoryName()+" successful!";
    }

    @DeleteMapping("/categories/removeFood/{foodId}")
    public String removeFoodFromCategory(@PathVariable int foodId){
        Food food = foodService.getFoodById(foodId);
        food.setTheCategory(null);
        foodService.saveFood(food);
        return "Remove food from this category successful";
    }

}
