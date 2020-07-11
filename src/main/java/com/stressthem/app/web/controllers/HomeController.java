package com.stressthem.app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {


    @GetMapping("/index")
    public String  index(){

        return "index";
    }

    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "<h1>Hi </h1>";
    }
}
