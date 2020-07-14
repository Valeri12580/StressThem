package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.AttackBindingModel;
import com.stressthem.app.services.interfaces.UserService;
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
import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/launch")
    public String homeLaunch(Model model, Principal principal){
        if(principal!=null){
            model.addAttribute("hasUserActivePlan",this.userService.hasUserActivePlan(principal.getName()));
        }

        if(!model.containsAttribute("attack")){
            model.addAttribute("attack",new AttackBindingModel());
        }

        return "home-launch-attack";
    }

    @PostMapping("/launch")
    public String postLaunch(@Valid @ModelAttribute AttackBindingModel attackBindingModel,
                             BindingResult result, RedirectAttributes redirectAttributes){
        System.out.println();
        return null;
    }
}
