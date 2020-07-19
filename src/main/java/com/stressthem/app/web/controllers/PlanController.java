package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.view.PlanViewModel;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/plans")
public class PlanController {
    private ModelMapper modelMapper;
    private PlanService planService;
    private UserService userService;

    @Autowired
    public PlanController(ModelMapper modelMapper, PlanService planService, UserService userService) {
        this.modelMapper = modelMapper;
        this.planService = planService;
        this.userService = userService;
    }


    @GetMapping
    public String plans(Model model){
        List<PlanViewModel> plans=List.of(this.modelMapper.map(this.planService.getAllPlans(),PlanViewModel[].class));
        model.addAttribute("plans",plans);
        System.out.println();
        //todo debugni za da vidish dto-tata v planservicemodel kak se predstavqt
        return "pricing";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") String id, Model model){
        model.addAttribute("plan",this.planService.getPlanById(id));
        model.addAttribute("id",id);
        return "confirm-order";

    }
    @PostMapping("/confirm/{id}")
    public String postConfirm(@PathVariable("id")  String id, Principal principal
    , RedirectAttributes redirectAttributes){
        try{
            this.userService.purchasePlan(id,principal.getName());
        }catch (UserPlanActivationException ex){
            redirectAttributes.addFlashAttribute("activationError",ex.getMessage());

            return String.format("redirect:/plans/confirm/%s",id);
        }

        return "redirect:/home/launch";
    }



}

//todo optinal v service and error handler here!