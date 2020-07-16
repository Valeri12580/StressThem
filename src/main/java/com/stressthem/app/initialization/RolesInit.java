package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value = 1)
public class RolesInit implements CommandLineRunner {

    private  final RoleRepository roleRepository;

    @Autowired
    public RolesInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count()==0){
            Role root=new Role("ROOT");
            Role admin=new Role("ADMIN");
            Role user=new Role("USER");

            this.roleRepository.saveAll(List.of(root,admin,user));
        }
    }
}
