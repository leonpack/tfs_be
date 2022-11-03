package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Restaurant;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantRestController {

    private RestaurantService restaurantService;
    private StaffService staffService;

    @Autowired
    public RestaurantRestController(RestaurantService theRestaurantService, StaffService theStaffService){
        restaurantService = theRestaurantService;
        staffService = theStaffService;
    }

    @GetMapping("/restaurants")
    @ApiOperation("Return list of all TFS's restaurant")
    public List<Restaurant> getAllRestaurant(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/restaurants/{restaurantId}")
    @ApiOperation("Return TFS restaurant based on restaurantId")
    public Restaurant getRestaurantById(@PathVariable String restaurantId){
        Restaurant theRestaurant = restaurantService.getRestaurantById(restaurantId);

        if(theRestaurant==null){
            throw new RuntimeException("Restaurant with id - " +restaurantId+" not found!");
        }
        return theRestaurant;
    }

    @PostMapping("/restaurants")
    @ApiOperation("Add new TFS restaurant (need full Restaurant's Json)")
    public String AddNewRestaurant(@RequestBody Restaurant theRestaurant){
        if(!restaurantService.checkDuplicateId(theRestaurant.getRestaurantId())){
            return "Duplicate with id -" +theRestaurant.getRestaurantId() + " has been found!, please try again";
        }
        if(!restaurantService.checkDuplicateName(theRestaurant.getRestaurantName())){
            return "This restaurant name - " +theRestaurant.getRestaurantName() + " has been taken, please try again";
        }
        if(!restaurantService.checkDuplicatePhoneNumber(theRestaurant.getRestaurantNumber())){
            return "This restaurant phone number - " +theRestaurant.getRestaurantNumber() + " has been linked with another restaurant, please try again!";
        }
        restaurantService.saveRestaurant(theRestaurant);
        return "Saved - " +theRestaurant;
    }

    @PutMapping("/restaurants")
    @ApiOperation("update existing TFS's Restaurant information (need full Restaurant's Json)")
    public Restaurant updateRestaurant(@RequestBody Restaurant theRestaurant){
        restaurantService.saveRestaurant(theRestaurant);
        return theRestaurant;
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    @ApiOperation("Disable existing TFS's Restaurant based on restaurantId")
    public String DisableRestaurant(@PathVariable String restaurantId){
        restaurantService.disableRestaurant(restaurantId);
        return "Disable restaurant with id - "+restaurantId + " completed!";
    }

    @PostMapping("/restaurants/{staffId}TO{restaurantId}")
    @ApiOperation("Add staff to restaurant(need 2 path variable: staffId & restaurantId)")
    public String addStaffToRestaurant(@PathVariable String staffId, @PathVariable String restaurantId){
        Staff theStaff = staffService.getStaffById(staffId);
        Restaurant theRestaurant = restaurantService.getRestaurantById(restaurantId);
        if(theStaff == null || theRestaurant == null){
            return "Staff or restaurant doesn't exist, please try again";
        }
        theRestaurant.addStaff(theStaff);
        return "Add " +theStaff + " to " +theRestaurant + " successfully";
    }

}
