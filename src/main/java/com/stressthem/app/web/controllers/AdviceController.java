package com.stressthem.app.web.controllers;

import com.stressthem.app.services.interfaces.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/adviceAPI")
public class AdviceController {

    private AdviceService service;

    @Autowired
    public AdviceController(AdviceService service) {
        this.service = service;
    }

    @GetMapping
    public String getAdvicePage() {
        return "advice";
    }

    @PostMapping
    public String postAdvice(RedirectAttributes redirect) {
        redirect.addFlashAttribute("advice", this.service.getRandomAdvice());
        return "redirect:/adviceAPI";
    }
}
