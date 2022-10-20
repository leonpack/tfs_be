package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> roleLists();

    public Role getRoleById(int roleId);

}
