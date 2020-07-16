package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.AttackBindingModel;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.view.AttackViewModel;
import com.stressthem.app.services.interfaces.AttackService;
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
import java.security.Principal;
import java.util.Arrays;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private AttackService attackService;
    private ModelMapper mapper;

    @Autowired
    public HomeController(UserService userService, AttackService attackService, ModelMapper mapper) {
        this.userService = userService;
        this.attackService = attackService;
        this.mapper = mapper;
    }

    @GetMapping("/launch")
    public String homeLaunch(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("hasUserActivePlan", this.userService.hasUserActivePlan(principal.getName()));

            model.addAttribute("attacksHistory", Arrays.asList(this.mapper
                    .map(this.attackService.getAllAttacksForCurrentUser(principal.getName()), AttackViewModel[].class)));
        }

        if (!model.containsAttribute("attack")) {
            model.addAttribute("attack", new AttackBindingModel());
        }



        return "home-launch-attack";
    }

    @PostMapping("/launch")
    public String postLaunch(@Valid @ModelAttribute AttackBindingModel attackBindingModel,
                             BindingResult result, RedirectAttributes redirectAttributes, Principal principal) {

        if (!this.userService.hasUserActivePlan(principal.getName())) {
            result.reject("errorCode1", "You dont have active plan!");
        }
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.attack", result);
            redirectAttributes.addFlashAttribute("attack", attackBindingModel);
            return "redirect:/home/launch";
        }
        //todo optimization and server,time validation to be dynamic created,max attack per day validation


        AttackServiceModel attackServiceModel = this.mapper.map(attackBindingModel, AttackServiceModel.class);

        this.attackService.launchAttack(attackServiceModel, principal.getName());

        return "redirect:/home/launch";
    }
}
