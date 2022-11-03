package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.StaffRepository;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImplementation implements StaffService{

    private StaffRepository staffRepository;

    @Autowired
    public StaffServiceImplementation(StaffRepository theStaffRepository){
        staffRepository = theStaffRepository;
    }

    @Override
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(String staffId) {
        Optional<Staff> result = staffRepository.findById(staffId);
        Staff theStaff = null;
        if(result.isPresent()){
            theStaff = result.get();
        } else {
            throw new RuntimeException("Staff with id - " +staffId+ " not found!");
        }
        return theStaff;
    }


    @Override
    public void saveStaff(Staff theStaff) {
        staffRepository.save(theStaff);
    }

    @Override
    public void disableStaff(String staffId) {
        Optional<Staff> result = staffRepository.findById(staffId);
        Staff theStaff = null;
        if(result.isPresent()){
            theStaff = result.get();
        }
        theStaff.setStaffStatus(false);
        staffRepository.save(theStaff);
    }

    @Override
    public boolean checkDuplicateStaffId(String staffId) {
        Optional<Staff> result = staffRepository.findById(staffId);
        if(result.isPresent()){
            throw new RuntimeException("Duplicate staff id - " +staffId+ " has been found, please try again");
        }
        return true;
    }
}
