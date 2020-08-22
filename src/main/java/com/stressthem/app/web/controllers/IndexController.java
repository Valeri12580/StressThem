package com.stressthem.app.web.controllers;

import com.stressthem.app.services.interfaces.EmailService;
import com.stressthem.app.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private EmailService emailService;



    @PageTitle("Welcome!")
    @GetMapping("/index")
    public String  index(){

        return "index";
    }


    @GetMapping("/email")
    public String email(){
        emailService.sendConfirmationEmail();
        return "index";
    }


}
