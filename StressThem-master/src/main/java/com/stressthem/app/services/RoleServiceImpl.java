package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.repositories.RoleRepository;
import com.stressthem.app.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }
}
