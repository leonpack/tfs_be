package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.AccountRepository;
import com.tfs.demo.tfs_crud_demo.dao.StaffRepository;
import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImplementation implements StaffService{

    private StaffRepository staffRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public StaffServiceImplementation(StaffRepository theStaffRepository,
                                      AccountRepository accountRepository){
        staffRepository = theStaffRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(int staffId) {
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
    public Staff getStaffByEmail(String email) {
        return staffRepository.getStaffByStaffEmail(email);
    }

    @Override
    public Staff getStaffByTheAccount(Account theAccount) {
        return staffRepository.getStaffByTheAccountForStaff(theAccount);
    }


    @Override
    public void saveStaff(Staff theStaff) {
        staffRepository.save(theStaff);
    }

    @Override
    public void disableStaff(int staffId) {
        Optional<Staff> result = staffRepository.findById(staffId);
        Staff theStaff = null;
        if(result.isPresent()){
            theStaff = result.get();
        }
        theStaff.setStaffStatus(false);
        staffRepository.save(theStaff);
    }

    @Override
    public boolean checkDuplicateAccountId(String accountId) {
        Optional<Account> result = accountRepository.findById(accountId);
        if(result.isPresent()){
            throw new RuntimeException("Account with id - " +accountId+ " has been found, please try again");
        }
        return true;
    }
}
