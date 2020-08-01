package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.service.CryptocurrencyServiceModel;
import com.stressthem.app.domain.models.view.PlanViewModel;
import com.stressthem.app.exceptions.UserPlanActivationException;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import com.stressthem.app.services.interfaces.PlanService;
import com.stressthem.app.services.interfaces.UserService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/plans")
public class PlanController {
    private ModelMapper modelMapper;
    private PlanService planService;
    private UserService userService;
    private CryptocurrencyService cryptocurrencyService;

    @Autowired
    public PlanController(ModelMapper modelMapper, PlanService planService, UserService userService, CryptocurrencyService cryptocurrencyService) {
        this.modelMapper = modelMapper;
        this.planService = planService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
    }


    @PageTitle("All plans")
    @GetMapping
    public String plans(Model model) {
        List<PlanViewModel> plans = List.of(this.modelMapper.map(this.planService.getAllPlans(), PlanViewModel[].class));
        model.addAttribute("plans", plans);
        System.out.println();
        //todo debugni za da vidish dto-tata v planservicemodel kak se predstavqt
        return "pricing";
    }

    @PageTitle("Confirm order")
    @GetMapping("/confirm/{id}")
    @PreAuthorize("isAuthenticated()")
    public String confirm(@PathVariable("id") String id, Model model) {
        model.addAttribute("plan", this.planService.getPlanById(id));
        model.addAttribute("crypto", this.cryptocurrencyService.getAllCryptocurrencies().stream()
                .map(CryptocurrencyServiceModel::getTitle).collect(Collectors.toList()));
        model.addAttribute("id", id);
        return "confirm-order";

    }

    @PostMapping("/confirm/{id}")
    public String postConfirm(@PathVariable("id") String id, @PathParam("cryptocurrency") String cryptocurrency, Principal principal
            , RedirectAttributes redirectAttributes) {
        try {
            this.userService.purchasePlan(id, principal.getName(), cryptocurrency);
        } catch (UserPlanActivationException ex) {
            redirectAttributes.addFlashAttribute("activationError", ex.getMessage());

            return String.format("redirect:/plans/confirm/%s", id);
        }

        return "redirect:/home/launch";
    }

    @GetMapping("/delete/{id}")
    public String deletePlan(@PathVariable("id")String id){
        this.planService.deletePlanById(id);
        return "redirect:/plans";
    }


}

//todo optinal v service and error handler here!