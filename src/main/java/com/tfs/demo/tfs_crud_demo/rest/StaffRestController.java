package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.StaffLoginDTO;
import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.CustomerService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StaffRestController {

    private StaffService staffService;
    private AccountService accountService;
    private CustomerService customerService;

    @Autowired
    public StaffRestController(StaffService theStaffService, AccountService theAccountService, CustomerService theCustomerService){
        staffService = theStaffService;
        accountService = theAccountService;
        customerService = theCustomerService;
    }

    @GetMapping("/staffs")
    public List<Staff> getAllStaffs(){
        return staffService.getAllStaffs();
    }

    @GetMapping("/staffs/{staffId}")
    public Staff getStaffById(@PathVariable int staffId){
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
        if(theStaff == null){
            throw new RuntimeException("Login success but this staff need to update their information");
        }
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
        if(!staffService.checkDuplicateAccountId(theStaff.getTheAccountForStaff().getAccountId())){
            throw new RuntimeException("Account with id" +theStaff.getTheAccountForStaff().getAccountId() + " already exist!");
        }
        if(!accountService.checkDuplicatePhoneNumber(theStaff.getTheAccountForStaff().getPhoneNumber())){
            throw new RuntimeException("This phone number is already linked with another account, please try again!");
        }
        //check duplicate email when adding new staff
//        if(staffService.getStaffByEmail(theStaff.getStaffEmail()).getStaffId()!=theStaff.getStaffId()
//        && customerService.getCustomerByEmail(theStaff.getStaffEmail()).getTheAccount().getAccountId()!=theStaff.getTheAccountForStaff().getAccountId()){
//            throw new RuntimeException("This email has already linked with another account, please try again");
//        }
        accountService.saveAccount(theStaff.getTheAccountForStaff());
        staffService.saveStaff(theStaff);
        return theStaff;
    }

    @PutMapping("/staffs")
    public Staff updateStaff(@RequestBody Staff theStaff){
        Staff theStaffFix = staffService.getStaffById(theStaff.getStaffId());

        if(theStaff.getTheAccountForStaff()!=null && theStaff.getTheAccountForStaff().getPhoneNumber()!=null && !theStaff.getTheAccountForStaff().getPhoneNumber().equals(theStaffFix.getTheAccountForStaff().getPhoneNumber())){
            Account accountCheck = accountService.checkLoginByPhone(theStaff.getTheAccountForStaff().getPhoneNumber());
            if (accountCheck==null){
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

                if(theStaff.getTheAccountForStaff()==null){
                    theStaff.setTheAccountForStaff(theStaffFix.getTheAccountForStaff());
                }
                accountService.saveAccount(theStaff.getTheAccountForStaff());
                staffService.saveStaff(theStaff);
                return theStaff;
            }
            else if(accountCheck!=null || !accountCheck.getAccountId().equals(theStaff.getTheAccountForStaff().getAccountId())){
                throw new RuntimeException("This phone number has already linked with another account!");
            }
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

        if(theStaff.getTheAccountForStaff()==null){
            theStaff.setTheAccountForStaff(theStaffFix.getTheAccountForStaff());
        }

        //TODO check duplicate phone number and email

//        if(theStaff.getTheAccountForStaff().getPhoneNumber()!=null){
//            Account accountCheck = accountService.checkLoginByPhone(theStaff.getTheAccountForStaff().getPhoneNumber());
//            if(!(accountCheck==null) || !accountCheck.getAccountId().equals(theStaff.getTheAccountForStaff().getAccountId())){
//                throw new RuntimeException("This phone number has already linked with another account!");
//            }
//        }
//        if(staffService.getStaffByEmail(theStaff.getStaffEmail()).getStaffId()!=theStaff.getStaffId()
//                && customerService.getCustomerByEmail(theStaff.getStaffEmail()).getTheAccount().getAccountId()!=theStaff.getTheAccountForStaff().getAccountId()){
//            throw new RuntimeException("This email has already linked with another account, please try again");
//        }
        accountService.saveAccount(theStaff.getTheAccountForStaff());
        staffService.saveStaff(theStaff);
        return theStaff;
    }

    @DeleteMapping("/staffs/{staffId}")
    public String disableStaff(@PathVariable int staffId){
        staffService.disableStaff(staffId);
        return "Disable staff with id - " +staffId + " completed!";
    }

}
