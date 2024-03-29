package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dto.LoginDTO;
import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StaffRestController {

    private final StaffService staffService;
    private final AccountService accountService;

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
    public Staff getStaffById(@PathVariable int staffId){
        return staffService.getStaffById(staffId);
    }

    @PostMapping("/staffs/login")
    public Staff loginForStaff(@RequestBody LoginDTO loginDTO){
        Account theAccount = accountService.getAccountById(loginDTO.getUsername());
        if(theAccount==null){
            throw new RuntimeException("Tài khoản không tồn tại!");
        }
        if(!theAccount.getPassword().equals(loginDTO.getPassword())){
            throw new RuntimeException("Sai mật khẩu!");
        }
        if(!theAccount.getRoleId().toString().equals("4")){
            throw new RuntimeException("Tài khoản này không được phép đăng nhập ở đây");
        }
        Staff theStaff = staffService.getStaffByTheAccount(theAccount);
        if(theStaff == null){
            throw new RuntimeException("Login success but this staff need to update their information");
        }
        return theStaff;
    }

    @PostMapping("/managers/login")
    public Staff loginForManager(@RequestBody LoginDTO loginDTO){
        Account theAccount = accountService.getAccountById(loginDTO.getUsername());
        if(theAccount==null){
            throw new RuntimeException("Tài khoản không tồn tại!");
        }
        if(!theAccount.getPassword().equals(loginDTO.getPassword())){
            throw new RuntimeException("Sai mật khẩu!");
        }
        if(theAccount.getRoleId().equals("4") || theAccount.getRoleId().equals("5")){
            throw new RuntimeException("Tài khoản này không được phép đăng nhập ở đây");
        }
        Staff theStaff = staffService.getStaffByTheAccount(theAccount);
        return theStaff;
    }

    @PostMapping("/staffs")
    public Staff addNewStaff(@RequestBody Staff theStaff){
        if(!staffService.checkDuplicateAccountId(theStaff.getTheAccountForStaff().getAccountId())){
            throw new RuntimeException("Account with id" +theStaff.getTheAccountForStaff().getAccountId() + " already exist!");
        }
        if(!accountService.checkDuplicatePhoneNumber(theStaff.getTheAccountForStaff().getPhoneNumber())){
            throw new RuntimeException("This phone number is already linked with another account, please try again!");
        }
        if(theStaff.getStaffAvatarUrl().isBlank() || theStaff.getStaffAvatarUrl()==null){
            theStaff.setStaffAvatarUrl("https://live.staticflickr.com/65535/52719475105_ec5b21e417_w.jpg");
        }
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

    @DeleteMapping("/staffs/{staffId}")
    public String disableStaff(@PathVariable int staffId){
        staffService.disableStaff(staffId);
        return "Disable staff with id - " +staffId + " completed!";
    }

}
