package com.stressthem.app.web;

import com.stressthem.app.web.annotations.PageTitle;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Throwable.class)
    @PageTitle("Oops...")
    public ModelAndView handle(Throwable ex, HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView("error");

        modelAndView.addObject("message",ex.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return modelAndView;
    }
}
