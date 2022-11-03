package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Staff;

import java.util.List;

public interface StaffService {

    public List<Staff> getAllStaffs();

    public Staff getStaffById(String staffId);
    public void saveStaff(Staff theStaff);

    public void disableStaff(String staffId);

    public boolean checkDuplicateStaffId(String staffId);

}
