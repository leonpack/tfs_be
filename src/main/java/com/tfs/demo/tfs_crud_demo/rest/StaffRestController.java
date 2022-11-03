package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Staff;
import com.tfs.demo.tfs_crud_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StaffRestController {

    private StaffService staffService;

    @Autowired
    public StaffRestController(StaffService theStaffService){
        staffService = theStaffService;
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

    @PostMapping("/staffs")
    public String addNewStaff(@RequestBody Staff theStaff){
        if(!staffService.checkDuplicateStaffId(theStaff.getStaffId())){
            return "Staff with this id - " +theStaff + " already exist, please try again!";
        }
        staffService.saveStaff(theStaff);
        return "Saved " +theStaff;
    }

    @PutMapping("/staffs")
    public Staff updateStaff(@RequestBody Staff theStaff){
        staffService.saveStaff(theStaff);
        return theStaff;
    }

    @DeleteMapping("/staffs/{staffId}")
    public String disableStaff(@PathVariable String staffId){
        staffService.disableStaff(staffId);
        return "Disable staff with id - " +staffId + " completed!";
    }

}
