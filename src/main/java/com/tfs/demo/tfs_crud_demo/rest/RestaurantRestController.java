package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Restaurant;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.RestaurantService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestaurantRestController {

    private final RestaurantService restaurantService;
    private final StaffService staffService;
    private final AccountService accountService;

    @Autowired
    public RestaurantRestController(RestaurantService theRestaurantService, StaffService theStaffService, AccountService theAccountService){
        restaurantService = theRestaurantService;
        staffService = theStaffService;
        accountService = theAccountService;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurant(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/restaurants/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable int restaurantId){
        Restaurant theRestaurant = restaurantService.getRestaurantById(restaurantId);
        return theRestaurant;
    }

    @GetMapping("/restaurants/manager/{restaurantId}")
    public Staff getManagerOfTheRestaurant(@PathVariable int restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Staff manager;
        for(Staff item : restaurant.getStaffList()){
            if(item.getTheAccountForStaff().getRoleId().toString().equals("3")){
                manager = item;
                return manager;
            }
        }
        return null;
    }

    @GetMapping("/manager/{managerId}")
    public Restaurant getRestaurantByManagerId(@PathVariable String managerId){
        Staff manager = staffService.getStaffByTheAccount(accountService.getAccountById(managerId));
        if(manager == null){
            throw new RuntimeException("This manager information is not found!");
        }
        if(manager.getTheRestaurant()==null){
            throw new RuntimeException("This manager doesn't work at any restaurant at the time");
        }
        return manager.getTheRestaurant();
    }

    @PostMapping("/restaurants")
    public String AddNewRestaurant(@RequestBody Restaurant theRestaurant){
        if(!restaurantService.checkDuplicatePhoneNumber(theRestaurant.getRestaurantNumber())){
            return "This restaurant phone number - " +theRestaurant.getRestaurantNumber() + " has been linked with another restaurant, please try again!";
        }
        if(theRestaurant.getAvailableStatus()==null){
            theRestaurant.setAvailableStatus(true);
        }
        restaurantService.saveRestaurant(theRestaurant);
        return "Saved - " +theRestaurant;
    }

    @PutMapping("/restaurants")
    public Restaurant updateRestaurant(@RequestBody Restaurant theRestaurant){
        Restaurant restaurant = restaurantService.getRestaurantById(theRestaurant.getRestaurantId());

        if(theRestaurant.getRestaurantLocation()==null){
            theRestaurant.setRestaurantLocation(restaurant.getRestaurantLocation());
        }
        if(theRestaurant.getRestaurantName()==null){
            theRestaurant.setRestaurantName(restaurant.getRestaurantName());
        }
        if(theRestaurant.getRestaurantNumber()==null){
            theRestaurant.setRestaurantNumber(restaurant.getRestaurantNumber());
        }
        if(theRestaurant.getLatitude()==null){
            theRestaurant.setLatitude(restaurant.getLatitude());
        }
        if(theRestaurant.getLongitude()==null){
            theRestaurant.setLongitude(restaurant.getLongitude());
        }
        if(theRestaurant.getStaffList()==null){
            theRestaurant.setStaffList(restaurant.getStaffList());
        }
        if(theRestaurant.getStatus()==null){
            theRestaurant.setStatus(restaurant.getStatus());
        }
        if(theRestaurant.getAvailableStatus()==null){
            theRestaurant.setAvailableStatus(restaurant.getAvailableStatus());
        }
        theRestaurant.setStaffList(theRestaurant.getStaffList());
        for(Staff item : theRestaurant.getStaffList()){
            Staff staff = staffService.getStaffById(item.getStaffId());
            staff.setTheRestaurant(theRestaurant);
        }
        restaurantService.saveRestaurant(theRestaurant);
        return theRestaurant;
    }

    @DeleteMapping("/restaurants/remove/{staffId}")
    public String removeStaffFromRestaurant(@PathVariable int staffId){
        Staff staff = staffService.getStaffById(staffId);
        staff.setTheRestaurant(null);
        staffService.saveStaff(staff);
        return "Staff has been removed from the restaurant";
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public String DisableRestaurant(@PathVariable int restaurantId){
        restaurantService.disableRestaurant(restaurantId);
        return "Disable restaurant with id - "+restaurantId + " completed!";
    }

//    @PostMapping("/restaurants/{staffId}TO{restaurantId}")
//    public String addStaffToRestaurant(@PathVariable int staffId, @PathVariable int restaurantId){
//        Staff theStaff = staffService.getStaffById(staffId);
//        Restaurant theRestaurant = restaurantService.getRestaurantById(restaurantId);
//        if(theStaff == null || theRestaurant == null){
//            return "Staff or restaurant doesn't exist, please try again";
//        }
//        theRestaurant.addStaff(theStaff);
//        return "Add " +theStaff + " to " +theRestaurant + " successfully";
//    }

    @GetMapping("/restaurants/busybutton/{restaurantId}")
    public ResponseEntity<String> changeRestaurantAvailableStatus(@PathVariable int restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if(restaurant.getAvailableStatus().toString().equals("true")){
            restaurant.setAvailableStatus(false);
            restaurantService.saveRestaurant(restaurant);
        } else if(restaurant.getAvailableStatus().toString().equals("false")){
            restaurant.setAvailableStatus(true);
            restaurantService.saveRestaurant(restaurant);
        }
        return ResponseEntity.ok("Cập nhật trạng thái nhà hàng thành công");
    }

    @GetMapping("/restaurants/available")
    public List<Restaurant> getAllAvailableRestaurant(){
        return restaurantService.getAllByAvailableStatus(true);
    }

}
