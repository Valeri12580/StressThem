package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.entities.Role;
import com.stressthem.app.domain.models.binding.AnnouncementBindingModel;
import com.stressthem.app.domain.models.binding.ArticleBindingModel;
import com.stressthem.app.domain.models.binding.CryptocurrencyBindingModel;
import com.stressthem.app.domain.models.binding.PlanBindingModel;
import com.stressthem.app.domain.models.service.*;
import com.stressthem.app.exceptions.ChangeRoleException;
import com.stressthem.app.exceptions.UserDeletionException;
import com.stressthem.app.services.interfaces.*;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
public class AdminPanelController {
    private UserService userService;
    private RoleService roleService;
    private AnnouncementService announcementService;
    private ModelMapper modelMapper;
    private ArticleService articleService;
    private CryptocurrencyService cryptocurrencyService;
    private PlanService planService;

    @Autowired
    public AdminPanelController(UserService userService, RoleService roleService, AnnouncementService announcementService, ModelMapper modelMapper, ArticleService articleService, CryptocurrencyService cryptocurrencyService, PlanService planService) {
        this.userService = userService;
        this.roleService = roleService;
        this.announcementService = announcementService;
        this.modelMapper = modelMapper;
        this.articleService = articleService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.planService = planService;
    }

    @PageTitle("Change roles")
    @GetMapping("/user-roles")
    public String changeRole(Model model) {
        //todo admin home page is user roles-to be restricted only to root admins
        model.addAttribute("users", this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));
        model.addAttribute("roles", this.roleService.getAllRoles().stream().map(Role::getName).filter(e -> !e.equals("USER")).collect(Collectors.toList()));
        return "admin-panel-user-roles";
    }

    @PostMapping("/user-roles")
    public String postChangeRole(@RequestParam String username, @RequestParam String role, @RequestParam String type
            , Principal principal, RedirectAttributes redirectAttributes) {
        try {
            this.userService.changeUserRole(username, role, type, principal);
        } catch (ChangeRoleException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/admin/user-roles";
    }

    @PageTitle("Delete user")
    @GetMapping("/delete-user")
    public String deleteUser(Model model) {
        model.addAttribute("users", this.userService.getAllUsers().stream().map(UserServiceModel::getUsername).collect(Collectors.toList()));

        return "admin-panel-delete-user";
    }

    @PostMapping("/delete-user")
    public String postDeleteUser(@RequestParam String username, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            this.userService.deleteUserByUsername(username, principal);
        } catch (UserDeletionException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/delete-user";
    }

    @PageTitle("Add announcement")
    @GetMapping("/add-announcement")
    public String addAnnouncement(Model model) {
        if (!model.containsAttribute("announcement")) {
            model.addAttribute("announcement", new AnnouncementBindingModel());
        }


        return "admin-panel-announcement-add";
    }

    @PostMapping("/add-announcement")
    public String postAddAnnouncement(@Valid @ModelAttribute("announcement") AnnouncementBindingModel announcementBindingModel,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("announcement", announcementBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.announcement", bindingResult);

        } else {
            this.announcementService.registerAnnouncement(this.modelMapper.map(announcementBindingModel, AnnouncementServiceModel.class)
                    , principal.getName());

        }


        return "redirect:/admin/add-announcement";
    }

    @PageTitle("Add article")
    @GetMapping("/add-article")
    public String addArticle(Model model) {
        if (!model.containsAttribute("article")) {
            model.addAttribute("article", new ArticleBindingModel());
        }

        return "admin-panel-article-add";

    }


    @PostMapping("/add-article")
    public String postAddArticle(@Valid @ModelAttribute ArticleBindingModel articleBindingModel, BindingResult bindingResult
            , RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("article", articleBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.article", bindingResult);
        } else {
            articleService.registerArticle(this.modelMapper.map(articleBindingModel, ArticleServiceModel.class), principal.getName());
        }

        return "redirect:/admin/add-article";
    }

    @PageTitle("Add cryptocurrency")
    @GetMapping("/add-cryptocurrency")
    public String addCryptocurrency(Model model) {
        if (!model.containsAttribute("cryptocurrency")) {
            model.addAttribute("cryptocurrency", new CryptocurrencyBindingModel());
        }

        return "admin-panel-add-cryptocurrency";
    }

    @PostMapping("/add-cryptocurrency")
    //todo unique cryptos only
    public String postAddCryptocurrency(@Valid @ModelAttribute CryptocurrencyBindingModel cryptocurrencyBindingModel, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes, Principal principal) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("cryptocurrency",cryptocurrencyBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cryptocurrency",bindingResult);
        }else{
            this.cryptocurrencyService.registerCryptocurrency(this.modelMapper.map(cryptocurrencyBindingModel, CryptocurrencyServiceModel.class)
                    ,principal.getName());
        }

        return "redirect:/admin/add-cryptocurrency";
    }

    @PageTitle("Add plan")
    @GetMapping("/add-plan")
    public String addPlan(Model model){
        if(!model.containsAttribute("plan")){
            model.addAttribute("plan",new PlanBindingModel());
        }


        return "admin-panel-add-plan";
    }


    @PostMapping("/add-plan")
    public String postAddPlan(@Valid @ModelAttribute PlanBindingModel planBindingModel,BindingResult result,RedirectAttributes redirectAttributes,
                              Principal principal){

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("plan",planBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.plan",result);
        }else{
            this.planService.register(this.modelMapper.map(planBindingModel,
                    PlanServiceModel.class),principal.getName());
        }

        return "redirect:/admin/add-plan";
    }


}
