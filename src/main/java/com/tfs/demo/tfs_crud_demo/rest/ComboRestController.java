package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.*;
import com.tfs.demo.tfs_crud_demo.service.CategoryService;
import com.tfs.demo.tfs_crud_demo.service.ComboService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import com.tfs.demo.tfs_crud_demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ComboRestController {

    private final ComboService comboService;
    private final FoodService foodService;
    private final CategoryService categoryService;
    private final RegionService regionService;

    @Autowired
    public ComboRestController(ComboService theComboService, FoodService theFoodService, CategoryService theCategoryService, RegionService theRegionService){
        comboService = theComboService;
        foodService = theFoodService;
        categoryService = theCategoryService;
        regionService = theRegionService;
    }

    @GetMapping("/combos")
    public List<Combo> getAllCombos(){
        return comboService.getAllCombos();
    }

    @GetMapping("/combos/{comboId}")
    public Combo findById(@PathVariable int comboId){
        return comboService.getComboById(comboId);
    }

    @PostMapping("/combos")
    public ResponseEntity<String> addNewCombo(@RequestBody Combo combo){
        if(combo.getImage()==null || combo.getImage().isBlank()){
            combo.setImage("https://live.staticflickr.com/65535/52821073294_019a4ab45d_m.jpg");
        }
        comboService.saveCombo(combo);
        StringBuilder description = new StringBuilder("");
        int bac = 0;
        int trung = 0;
        int nam = 0;
        for(ComboDetail item: combo.getComboItems()){
            Food food = foodService.getFoodById(item.getFoodId());
            if(food.getTheRegion().getId()==1){
                bac += 1;
            } else if (food.getTheRegion().getId()==2){
                trung += 1;
            } else {
                nam += 1;
            }
        }
        for(ComboDetail item: combo.getComboItems()){
            description.append(" " +item.getQuantity() +" " + item.getName() + " ,");
        }
        if(bac >= trung && bac >= nam){
            Region theRegion = regionService.getRegionById(1);
            Category theCategory = categoryService.getCategoryById(13);
            Food food = new Food("Combo " + combo.getComboName(), description.toString(), combo.getComboPrice(), combo.getImage(), theCategory, theRegion, true, 0, 0, true);
            foodService.saveFood(food);
        }
        else if(trung >= bac && trung >= nam){
            Region theRegion = regionService.getRegionById(2);
            Category theCategory = categoryService.getCategoryById(13);
            Food food = new Food("Combo " + combo.getComboName(), description.toString(), combo.getComboPrice(), combo.getImage(), theCategory, theRegion, true, 0, 0, true);
            foodService.saveFood(food);
        }
        else if(nam >= bac && nam >= trung){
            Region theRegion = regionService.getRegionById(3);
            Category theCategory = categoryService.getCategoryById(13);
            Food food = new Food("Combo " + combo.getComboName(), description.toString(), combo.getComboPrice(), combo.getImage(), theCategory, theRegion, true, 0, 0, true);
            foodService.saveFood(food);
        }
        return ResponseEntity.ok("Tạo combo thành công");
    }

    @PutMapping("/combos")
    public ResponseEntity<String> updateCombo(@RequestBody Combo combo){
        Combo existCombo = comboService.getComboById(combo.getId());

        if(combo.getComboName()==null){
            combo.setComboName(existCombo.getComboName());
        }
        if(combo.getComboPrice()==null){
            combo.setComboPrice(existCombo.getComboPrice());
        }
        if(combo.getImage()==null){
            combo.setImage(existCombo.getImage());
        }
        if(combo.getDescription()==null){
            combo.setDescription(existCombo.getDescription());
        }
        combo.setComboItems(combo.getComboItems());
        if(combo.getComboItems()==null || combo.getComboItems().isEmpty()){
            combo.setComboItems(existCombo.getComboItems());
        }
        if(combo.getStatus()==null){
            combo.setStatus(existCombo.getStatus());
        }

        //also gen to food, get existCombo name to prevent unable to update due to different combo name
        Food existFood = foodService.getByName(existCombo.getComboName());
        if(existFood==null){
            throw new RuntimeException("This combo/food is not exist, or the name has been changed");
        }

        comboService.saveCombo(combo);

        StringBuilder description = new StringBuilder("");
        int bac = 0;
        int trung = 0;
        int nam = 0;
        for(ComboDetail item: combo.getComboItems()){
            description.append(" " +item.getQuantity() +" " + item.getName() + " ,");
        }
        for(ComboDetail item: combo.getComboItems()){
            Food food = foodService.getFoodById(item.getFoodId());
            if(food.getTheRegion().getId()==1){
                bac += 1;
            } else if (food.getTheRegion().getId()==2){
                trung += 1;
            } else {
                nam += 1;
            }
        }

        if(bac >= trung && bac >= nam){
            existFood.setDescription(description.toString());
            existFood.setImgUrl(combo.getImage());
            existFood.setPrice(combo.getComboPrice());
            existFood.setFoodName(combo.getComboName());
            Region theRegion = regionService.getRegionById(1);
            existFood.setTheRegion(theRegion);
            foodService.saveFood(existFood);
        }
        else if(trung >= bac && trung >= nam){
            existFood.setDescription(description.toString());
            existFood.setImgUrl(combo.getImage());
            existFood.setFoodName(combo.getComboName());
            existFood.setPrice(combo.getComboPrice());
            Region theRegion = regionService.getRegionById(2);
            existFood.setTheRegion(theRegion);
            foodService.saveFood(existFood);
        } else if(nam >= bac && nam >= trung){
            existFood.setDescription(description.toString());
            existFood.setImgUrl(combo.getImage());
            existFood.setPrice(combo.getComboPrice());
            existFood.setFoodName(combo.getComboName());
            Region theRegion = regionService.getRegionById(3);
            existFood.setTheRegion(theRegion);
            foodService.saveFood(existFood);
        }
        return ResponseEntity.ok("Cập nhật combo thành công");
    }

    @PutMapping("/combos/clear/{comboId}")
    public Combo clearComboItem(@PathVariable int comboId){
        Combo combo = comboService.getComboById(comboId);
        combo.setComboItems(null);
        return combo;
    }

    @DeleteMapping("/combos/{comboId}")
    public String disableCombo(@PathVariable int comboId){
        comboService.disableComboById(comboId);
        return "Disabled combo " +comboId + " done!";
    }

}
