package com.stressthem.app.repositories;

import com.stressthem.app.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findRoleByName(String role);

}
