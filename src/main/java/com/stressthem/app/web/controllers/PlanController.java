package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.view.PlanViewModel;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String postConfirm(@PathVariable  String id, Principal principal){

    }

}
