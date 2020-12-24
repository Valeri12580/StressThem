package com.stressthem.app.initialization;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.repositories.UserRepository;
import com.stressthem.app.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(value = 2)
public class UsersInit implements CommandLineRunner {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    @Autowired
    public UsersInit(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.userRepository.count() == 0) {

            User admin = new User("valeri12580", passwordEncoder.encode("12345678"), "valeri125@dir.bg",
                    "https://i.ytimg.com/vi/WhIrvsbEJ6Q/maxresdefault.jpg",
                    LocalDateTime.now(ZoneId.systemDefault()), null,
                    new HashSet<>(this.roleService.getAllRoles().stream().filter(e->!e.getName().equals("UNCONFIRMED")).collect(Collectors.toSet())),
                    null, null,null,null,null,null);


            User user = new User("test",
                    passwordEncoder.encode("test1234"),
                    "test@dir.bg", "", LocalDateTime.now(ZoneId.systemDefault()), null, Set.of(this.roleService.getRoleByName("USER")), null, null
            ,null,null,null,null);

            User unconfirmed=new User("unconfirmed_user",passwordEncoder.encode("12345678"),"valeri125we@gmail.com","",LocalDateTime.now(ZoneId.systemDefault()),null,Set.of(roleService.getRoleByName("UNCONFIRMED")),
                    null,null,null,null,null,null);


            userRepository.save(admin);
            userRepository.save(user);
            userRepository.save(unconfirmed);


        }


    }
}
