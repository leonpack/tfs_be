package com.tfs.demo.tfs_crud_demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;
//
//    @OneToMany(mappedBy = "theRole", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
//    @JsonManagedReference(value = "acc_role")
//    private List<Account> accountList;

    public Role(){

    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    //    public void addAccount(Account theAccount){
//        if(accountList==null){
//            accountList = new ArrayList<>();
//        }
//        accountList.add(theAccount);
//    }
}
