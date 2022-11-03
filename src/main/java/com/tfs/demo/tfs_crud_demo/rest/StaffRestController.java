package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.AccountService;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @ApiOperation("Return list of all staffs")
    public List<Staff> getAllStaffs(){
        return staffService.getAllStaffs();
    }

    @GetMapping("/staffs/{staffId}")
    @ApiOperation("Return staff based on staffId")
    public Staff getStaffById(@PathVariable String staffId){
        Staff theStaff = staffService.getStaffById(staffId);
        return theStaff;
    }

    @PostMapping("/staffs/{accountId}")
    @ApiOperation("Add new Staff (Need to create Account first, " +
            "then put accountId from that created Account to PathVariable {accountId} to link between account and staff)" +
            "(need full Staff's Json and accountID(this accountId must not linked with any other specific role such as other staff, other customer, etc...))")
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
    @ApiOperation("update existing staff(Need full Staff's Json)")
    public Staff updateStaff(@RequestBody Staff theStaff){
        staffService.saveStaff(theStaff);
        return theStaff;
    }

    @DeleteMapping("/staffs/{staffId}")
    @ApiOperation("Disable existing staff based on staffId")
    public String disableStaff(@PathVariable String staffId){
        staffService.disableStaff(staffId);
        return "Disable staff with id - " +staffId + " completed!";
    }

}
