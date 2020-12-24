package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.UserLoginBindingModel;
import com.stressthem.app.domain.models.binding.UserRegisterBindingModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.domain.models.view.ProfileEditViewModel;
import com.stressthem.app.exceptions.DuplicatedEmailException;
import com.stressthem.app.exceptions.DuplicatedUsernameException;
import com.stressthem.app.services.interfaces.UserService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

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


    @GetMapping("/profile/verification")
    @ResponseBody
    public ResponseEntity<Void> sendConfirmationCode(Principal principal) {
        userService.sendConfirmationEmail(principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/profile/verification", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> confirmConfirmationCode(@RequestBody String code, Principal principal) {

        if (userService.confirmConfirmationCode(code, principal.getName())) {
            ResponseEntity.ok().build();

        }

        return ResponseEntity.badRequest().build();
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
    public String login(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserLoginBindingModel());
        }

        return "login";
    }


    @PageTitle("Profile")
    @PreAuthorize("@userSecurityAccessChecker.canAccess(authentication,#username)")
    @GetMapping("/profile/{username}")
    public String profileEdit(@PathVariable String username, Model model) {
        ProfileEditViewModel profile = this.modelMapper.map(this.userService.getUserByUsername(username), ProfileEditViewModel.class);
        if (!model.containsAttribute("userEdit")) {
            //todo custom mapping
            model.addAttribute("userEdit", profile);
        }

        return "profile-edit";
    }


    @PostMapping("/profile/{username}")
    public String postProfileEdit(@PathVariable String username, @Valid @ModelAttribute("userEdit") ProfileEditViewModel profileEditViewModel, BindingResult result
            , RedirectAttributes redirectAttributes) {

        try {
            userService.validateUsers(username, this.modelMapper.map(profileEditViewModel, UserServiceModel.class));
        } catch (DuplicatedEmailException ex) {
            result.rejectValue("email", "error1", ex.getMessage());
        } catch (DuplicatedUsernameException ex) {
            result.rejectValue("username", "error", ex.getMessage());
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEdit", result);
            redirectAttributes.addFlashAttribute("userEdit", profileEditViewModel);
            return String.format("redirect:/users/profile/%s", username);
        }
        this.userService.updateUser(username, this.modelMapper.map(profileEditViewModel, UserServiceModel.class));
        SecurityContextHolder.clearContext();
        return "redirect:/users/login";
    }



    @PreAuthorize("@userSecurityAccessChecker.canAccess(authentication,#id)")
    @GetMapping("/profile/delete/{id}")
    public String deleteProfile(@PathVariable String id, HttpSession session) {

        this.userService.deleteUserById(id);
        session.invalidate();

        return "redirect:/index";
    }


}
