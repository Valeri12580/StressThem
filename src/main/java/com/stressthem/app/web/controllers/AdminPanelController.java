package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.ChangeRoleException;
import com.stressthem.app.exceptions.UserDeletionException;
import com.stressthem.app.services.interfaces.RoleService;
import com.stressthem.app.services.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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
    public String changeRole(Model model) {
        //todo admin home page is user roles-to be restricted only to root admins
        model.addAttribute("users", this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));
        model.addAttribute("roles", this.roleService.getAllRoles().stream().map(Role::getName).filter(e -> !e.equals("USER")).collect(Collectors.toList()));
        return "admin-panel-user-roles";
    }

    @PostMapping("/user-roles")
    public String postChangeRoles(@RequestParam String username, @RequestParam String role, @RequestParam String type
            , Principal principal, RedirectAttributes redirectAttributes) {
        try {
            this.userService.changeUserRole(username, role, type,  principal);
        } catch (ChangeRoleException ex) {
            redirectAttributes.addFlashAttribute("error",ex.getMessage());
        }

        return "redirect:/admin/user-roles";
    }

    @GetMapping("/delete-user")
    public String deleteUser(Model model){
        model.addAttribute("users", this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));

        return "admin-panel-delete-user";
    }

    @PostMapping("/delete-user")
    public String postDeleteUser(@RequestParam String username,Principal principal,RedirectAttributes redirectAttributes){
        try {
            this.userService.deleteUserByUsername(username,principal);
        }catch (UserDeletionException ex){
            redirectAttributes.addFlashAttribute("error",ex.getMessage());
        }
        return "redirect:/admin/delete-user";
    }
}
