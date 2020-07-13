package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.UserRegisterBindingModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRegisterBindingModel());
        }

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute UserRegisterBindingModel user
            , BindingResult result, RedirectAttributes redirectAttributes) {


        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/register";

        }

        this.userService.register(this.modelMapper.map(user, UserServiceModel.class));

        return "redirect:/users/login";
    }
}
