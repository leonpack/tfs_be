package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Category;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.CategoryService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

    private CategoryService categoryService;

    private FoodService foodService;

    @Autowired
    public CategoryRestController(CategoryService theCategoryService, FoodService theFoodService){
        categoryService = theCategoryService;
        foodService = theFoodService;
    }

    @GetMapping("/categories")
    @ApiOperation("Return list of all categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    @ApiOperation("Return category based on categoryId")
    public Category getCategoryById(@PathVariable String categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);

        if(theCategory == null){
            throw new RuntimeException("Category with id - " +categoryId + " not found!");
        }
        return theCategory;
    }

    @PostMapping("/categories")
    @ApiOperation("Add new category (Need full Category's JSON)")
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
    @ApiOperation("Update existing category (Needd full Category's JSON)")
    public Category updateCategory(@RequestBody Category theCategory){
        categoryService.saveCategory(theCategory);
        return theCategory;
    }

    @DeleteMapping("/categories/{categoryId}")
    @ApiOperation("Disable existing category based on id")
    public String disableCategory(@PathVariable String categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);

        if(theCategory == null){
            throw new RuntimeException("Category with id - " +categoryId + " not found!!");
        }

        theCategory.setStatus(false);
        categoryService.saveCategory(theCategory);

        return "Disable category with id - " +categoryId + " completed!";
    }

    @PostMapping("/categories/{foodId}TO{categoryId}")
    @ApiOperation("Add Food to Category")
    public String addFoodToCategory(@PathVariable String categoryId,@PathVariable int foodId){
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

}
