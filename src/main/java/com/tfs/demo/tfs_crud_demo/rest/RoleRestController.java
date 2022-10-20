package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Role;
import com.tfs.demo.tfs_crud_demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleRestController {

    private RoleService roleService;

    @Autowired
    public RoleRestController(RoleService theRoleService){
        roleService = theRoleService;
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.roleLists();
    }

    @GetMapping("/roles/{roleId}")
    public Role getRoleById(@PathVariable int roleId){
        Role theRole = roleService.getRoleById(roleId);
        if(theRole==null){
            throw new RuntimeException("Role with id - " +roleId+" not found!");
        }
        return theRole;
    }


}
