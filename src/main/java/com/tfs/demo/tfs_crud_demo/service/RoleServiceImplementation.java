package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.RoleRepository;
import com.tfs.demo.tfs_crud_demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplementation implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImplementation(RoleRepository theRoleRepository){
        roleRepository = theRoleRepository;
    }

    @Override
    public List<Role> roleLists() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int roleId) {
        Optional<Role> result = roleRepository.findById(roleId);

        Role theRole = null;
        if(result.isPresent()){
            theRole = result.get();
        } else {
            throw new RuntimeException("Role with id - " +roleId + " not found!");
        }
        return theRole;
    }

}
