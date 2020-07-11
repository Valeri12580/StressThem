package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.UserRegisterBindingModel;
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

        System.out.println();
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/register";

        }
        return "/users/login";
    }
}
