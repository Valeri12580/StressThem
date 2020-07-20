package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String authority);

    List<Role> getAllRoles();


}
