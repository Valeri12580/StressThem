package com.stressthem.app.web;

import com.stressthem.app.web.annotations.PageTitle;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Throwable.class)
    @PageTitle("Oops...")
    public ModelAndView handle(Throwable ex){
        ModelAndView modelAndView=new ModelAndView("error");

        modelAndView.addObject("message",ex.getMessage());
        return modelAndView;
    }
}
