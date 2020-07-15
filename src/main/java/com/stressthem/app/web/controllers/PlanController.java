package com.stressthem.app.web.controllers;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plans")
public class PlanController {
    private ModelMapper modelMapper;

    @Autowired
    public PlanController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public String plans(){

        //todo debugni za da vidish dto-tata v planservicemodel kak se predstavqt
        return "pricing";
    }
}
