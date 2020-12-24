package com.stressthem.app.web.controllers;

import com.stressthem.app.web.annotations.PageTitle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @PageTitle("Welcome!")
    @GetMapping("/index")
    public String index() {
        return "index";
    }


}
