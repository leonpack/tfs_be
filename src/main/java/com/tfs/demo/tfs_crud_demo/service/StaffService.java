package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;

import java.util.List;

public interface StaffService {

    public List<Staff> getAllStaffs();

    public Staff getStaffById(String staffId);

    public Staff getStaffByTheAccount(Account theAccount);

    public void saveStaff(Staff theStaff);

    public void disableStaff(String staffId);

    public boolean checkDuplicateStaffId(String staffId);

    public boolean checkDuplicateAccountId(String accountId);

}
