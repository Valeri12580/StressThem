package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.services.interfaces.RoleService;
import com.stressthem.app.services.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    private UserService userService;
    private RoleService roleService;

    public AdminPanelController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/user-roles")
    public String changeRole(Model model){
        model.addAttribute("users",this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));
        model.addAttribute("roles",this.roleService.getAllRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return "admin-panel-user-roles";
    }

    @PostMapping("/user-roles")
    public String postChangeRoles(){
        return "redirect:/admin/user-roles";
    }
}
