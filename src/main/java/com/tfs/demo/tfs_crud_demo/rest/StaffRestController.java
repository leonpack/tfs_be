package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StaffRestController {

    private StaffService staffService;
    private AccountService accountService;

    @Autowired
    public StaffRestController(StaffService theStaffService, AccountService theAccountService){
        staffService = theStaffService;
        accountService = theAccountService;
    }

    @GetMapping("/staffs")
    public List<Staff> getAllStaffs(){
        return staffService.getAllStaffs();
    }

    @GetMapping("/staffs/{staffId}")
    public Staff getStaffById(@PathVariable String staffId){
        Staff theStaff = staffService.getStaffById(staffId);
        return theStaff;
    }

    @PostMapping("/staffs/{accountId}")
    public String addNewStaff(@RequestBody Staff theStaff,@PathVariable String accountId){
        if(!staffService.checkDuplicateStaffId(theStaff.getStaffId())){
            return "Staff with this id - " +theStaff + " already exist, please try again!";
        }
        Account theAccount = accountService.getAccountById(accountId);
        theStaff.setTheAccountForStaff(theAccount);
        staffService.saveStaff(theStaff);
        return "Saved " +theStaff;
    }

    @PutMapping("/staffs")
    public Staff updateStaff(@RequestBody Staff theStaff){
        Staff theStaffFix = staffService.getStaffById(theStaff.getStaffId());
        if(theStaff.getTheAccountForStaff()==null){
            theStaff.setTheAccountForStaff(theStaffFix.getTheAccountForStaff());
        }
        if(theStaff.getTheRestaurant()==null){
            theStaff.setTheRestaurant(theStaffFix.getTheRestaurant());
        }
        staffService.saveStaff(theStaff);
        return theStaff;
    }

    @DeleteMapping("/staffs/{staffId}")
    public String disableStaff(@PathVariable String staffId){
        staffService.disableStaff(staffId);
        return "Disable staff with id - " +staffId + " completed!";
    }

    @PostMapping("/staff/{staffId}SET{imageUrl}")
    public String updateStaffAvatar(@PathVariable String staffId, @PathVariable String imageUrl){
        Staff theStaff = staffService.getStaffById(staffId);
        theStaff.setStaffAvatarUrl(imageUrl);
        return "Set avatar for staff " +theStaff+ " successfully!";
    }

}
