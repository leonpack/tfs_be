package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.entity.Region;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import com.tfs.demo.tfs_crud_demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RegionRestController {
    private RegionService regionService;
    private FoodService foodService;

    @Autowired
    public RegionRestController(RegionService theRegionService, FoodService theFoodService){
        regionService = theRegionService;
        foodService = theFoodService;
    }

    @GetMapping("/regions")
    public List<Region> getAllRegion(){
        return regionService.getAllRegions();
    }

    @GetMapping("/regions/{regionId}")
    public Region getRegionById(@PathVariable int regionId){
        Region theRegion = regionService.getRegionById(regionId);
        if(theRegion == null){
            throw new RuntimeException("Region with id - " +regionId + " not found!");
        }
        return theRegion;
    }

    @PostMapping("/regions/{foodId}TO{regionId}")
    public String addFoodToRegion(@PathVariable int regionId,@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        Region theRegion = regionService.getRegionById(regionId);
        if(theRegion==null){
            throw new RuntimeException("Region with id - " +regionId + " not found!");
        }
        theRegion.addFood(theFood);
        theFood.setTheRegion(theRegion);
        regionService.saveRegion(theRegion);
        foodService.saveFood(theFood);
        return "Add food: " +theFood.getFoodName() + " to region: " +theRegion.getRegion_name() + " successfull";
    }

    @PutMapping("/regions")
    public Region updateRegion(@RequestBody Region theRegion){
        Region existRegion = regionService.getRegionById(theRegion.getId());
        if(theRegion.getRegion_name()==null){
            theRegion.setRegion_name(existRegion.getRegion_name());
        }
        if(theRegion.getFoodList()==null){
            theRegion.setFoodList(existRegion.getFoodList());
        }
        theRegion.setFoodList(theRegion.getFoodList());
        for(Food item: theRegion.getFoodList()){
            Food food = foodService.getFoodById(item.getId());
            food.setTheRegion(theRegion);
        }
        regionService.saveRegion(theRegion);
        return theRegion;
    }

}
