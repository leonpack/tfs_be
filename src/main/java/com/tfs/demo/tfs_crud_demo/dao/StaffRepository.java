package com.tfs.demo.tfs_crud_demo.dao;

import com.tfs.demo.tfs_crud_demo.entity.Account;
import com.tfs.demo.tfs_crud_demo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface StaffRepository extends JpaRepository<Staff, String> {

    Staff getStaffByTheAccountForStaff(Account theAccount);

    Staff getStaffByStaffEmail(String mail);

}
