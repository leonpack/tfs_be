package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.StaffLoginDTO;
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

    @PostMapping("/staffs/login")
    public Staff loginForStaff(@RequestBody StaffLoginDTO loginDTO){
        Account theAccount = accountService.getAccountById(loginDTO.getUsername());
        if(theAccount==null){
            throw new RuntimeException("username not found!");
        }
        if(!theAccount.getPassword().equals(loginDTO.getPassword())){
            throw new RuntimeException("Wrong password!");
        }
        if(theAccount.getRoleId().toString().equals("5")){
            throw new RuntimeException("This type of account is not permit here");
        }
        Staff theStaff = staffService.getStaffByTheAccount(theAccount);
        return theStaff;
    }

//    @PostMapping("/staffs/{accountId}")
//    public String addNewStaff(@RequestBody Staff theStaff,@PathVariable String accountId){
//        if(!staffService.checkDuplicateStaffId(theStaff.getStaffId())){
//            return "Staff with this id - " +theStaff + " already exist, please try again!";
//        }
//        Account theAccount = accountService.getAccountById(accountId);
//        theStaff.setTheAccountForStaff(theAccount);
//        staffService.saveStaff(theStaff);
//        return "Saved " +theStaff;
//    }

    @PostMapping("/staffs")
    public Staff addNewStaff(@RequestBody Staff theStaff){
        if(!staffService.checkDuplicateStaffId(theStaff.getStaffId())){
            throw new RuntimeException("Staff with id " +theStaff.getStaffId()+ " already exist");
        }
        if(!staffService.checkDuplicateAccountId(theStaff.getTheAccountForStaff().getAccountId())){
            throw new RuntimeException("Account with id" +theStaff.getTheAccountForStaff().getAccountId() + " already exist!");
        }
        accountService.saveAccount(theStaff.getTheAccountForStaff());
        staffService.saveStaff(theStaff);
        return theStaff;
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
        if(theStaff.getStaffEmail()==null){
            theStaff.setStaffEmail(theStaffFix.getStaffEmail());
        }
        if(theStaff.getStaffActivityStatus()==null){
            theStaff.setStaffActivityStatus(theStaffFix.getStaffActivityStatus());
        }
        if(theStaff.getStaffAvatarUrl()==null){
            theStaff.setStaffAvatarUrl(theStaffFix.getStaffAvatarUrl());
        }
        if(theStaff.getStaffFullName()==null){
            theStaff.setStaffFullName(theStaffFix.getStaffFullName());
        }
        accountService.saveAccount(theStaff.getTheAccountForStaff());
        staffService.saveStaff(theStaff);
        return theStaff;
    }

    @DeleteMapping("/staffs/{staffId}")
    public String disableStaff(@PathVariable String staffId){
        staffService.disableStaff(staffId);
        return "Disable staff with id - " +staffId + " completed!";
    }

}
