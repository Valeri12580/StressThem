package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.binding.AttackBindingModel;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.view.AnnouncementViewModel;
import com.stressthem.app.domain.models.view.AttackViewModel;
import com.stressthem.app.services.interfaces.AnnouncementService;
import com.stressthem.app.services.interfaces.AttackService;
import com.stressthem.app.services.interfaces.UserService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private AttackService attackService;
    private ModelMapper mapper;
    private AnnouncementService announcementService;

    @Autowired
    public HomeController(UserService userService, AttackService attackService, ModelMapper mapper, AnnouncementService announcementService) {
        this.userService = userService;
        this.attackService = attackService;
        this.mapper = mapper;
        this.announcementService = announcementService;
    }

    @ResponseBody
    @GetMapping("/launch/refresh")
    public ResponseEntity<List<AttackViewModel>>getAllAttacksForCurrentUser(Principal principal){
        return ResponseEntity.ok(Arrays.asList(this.mapper
                .map(this.attackService.getAllAttacksForCurrentUser(principal.getName()), AttackViewModel[].class) ));
    }


    @GetMapping("/launch/clear")
    public ResponseEntity<String>clearAllAttacksForCurrentUser(Principal principal){
        this.attackService.clearAttacks(principal.getName());
        return ResponseEntity.ok().build();
    }

    @PageTitle("Launch attack")
    @GetMapping("/launch")
    public String launch(Model model, Authentication authentication) {
        String userId = ((User) authentication.getPrincipal()).getId();
        String username = ((User) authentication.getPrincipal()).getUsername();
        if (username != null) {
            model.addAttribute("hasUserActivePlan", this.userService.hasUserActivePlan(username));

            model.addAttribute("attacksHistory", Arrays.asList(this.mapper
                    .map(this.attackService.getAllAttacksForCurrentUser(username), AttackViewModel[].class)));
            model.addAttribute("availableAttacks", this.userService.getUserAvailableAttacks(username));
            model.addAttribute("userId", userId);
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
            result.reject("errorCode1", "You dont have an active plan!");
        } else {
            this.attackService.validateAttack(attackBindingModel.getTime(), attackBindingModel.getServers(), principal.getName(),
                    result);
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.attack", result);
            redirectAttributes.addFlashAttribute("attack", attackBindingModel);
            return "redirect:/home/launch";
        }


        AttackServiceModel attackServiceModel = this.mapper.map(attackBindingModel, AttackServiceModel.class);
        attackServiceModel = this.attackService.setAttackExpiredOn(attackBindingModel.getTime(), attackServiceModel);

        this.attackService.launchAttack(attackServiceModel, principal.getName());

        return "redirect:/home/launch";
    }


    @PageTitle("Announcements")
    @GetMapping("/announcements")
    public String announcements(Model model) {
        model.addAttribute("announcements",
                Arrays.asList(this.mapper.map(this.announcementService.getAllAnnouncements(),
                        AnnouncementViewModel[].class)));


        return "home-announcements";
    }

    @GetMapping("/announcements/delete/{id}")
    public String deleteAnnouncement(@PathVariable("id") String id, Authentication authentication) {


        this.announcementService.deleteAnnouncementById(id);
        return "redirect:/home/announcements";
    }
}
