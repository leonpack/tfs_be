package com.tfs.demo.tfs_crud_demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

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
