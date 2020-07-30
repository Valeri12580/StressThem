package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.UserLoginBindingModel;
import com.stressthem.app.domain.models.binding.UserRegisterBindingModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.domain.models.view.ProfileEditViewModel;
import com.stressthem.app.services.interfaces.UserService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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


    @PageTitle("Register")
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

        this.userService.register(this.modelMapper.map(user, UserServiceModel.class));

        return "redirect:/users/login";
    }


    @PageTitle("Login")
    @GetMapping("/login")
    public String login(Model model){
        if(!model.containsAttribute("user")){
            model.addAttribute("user",new UserLoginBindingModel());
        }

        return "login";
    }


    @PageTitle("Profile")
    @GetMapping("/profile/{id}")
    public String profileEdit(@PathVariable String id, Model model){
        ProfileEditViewModel profile=this.modelMapper.map(this.userService.findUserById(id),ProfileEditViewModel.class);
        if(!model.containsAttribute("userEdit")){
            //todo custom mapping
            model.addAttribute("userEdit",profile);
        }

        return "profile-edit";
    }


    @PostMapping("/profile/{id}")
    public String postProfileEdit(@Valid @ModelAttribute("userEdit") ProfileEditViewModel profileEditViewModel,BindingResult result
            ,RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEdit", result);
            redirectAttributes.addFlashAttribute("userEdit", profileEditViewModel);

        }else{
            this.userService.updateUser(this.modelMapper.map(profileEditViewModel,UserServiceModel.class));
            redirectAttributes.addFlashAttribute("editUser",profileEditViewModel);
        }

        //todo fix profie edit when changing only one field-to be changed ony that fix this!!!
        return String.format("redirect:/users/profile/%s",profileEditViewModel.getId());
    }

    //todo post maybe?
    @GetMapping("/profile/delete/{id}")
    public String deleteProfile(@PathVariable String id, HttpSession session){


        this.userService.deleteUserById(id);
        session.invalidate();

        return "redirect:/index";
    }




}
