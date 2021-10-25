package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.WeatherBindingModel;
import com.stressthem.app.domain.models.view.weather.WeatherViewModel;
import com.stressthem.app.services.interfaces.WeatherService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/weatherAPI")
public class WeatherController {

    private WeatherService service;
    private ModelMapper modelMapper;

    @Autowired
    public WeatherController(WeatherService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PageTitle("Weather API")
    @GetMapping
    public String getWeatherPage(Model model){
        if(!model.containsAttribute("weatherBindingModel")){
           model.addAttribute("weatherBindingModel", new WeatherBindingModel());
        }
        if(!model.containsAttribute("weatherViewModel")){
            model.addAttribute("weatherViewModel", null);
        }

        return "weather";
    }

    @PostMapping
    public String postWeatherPage(@Valid @ModelAttribute WeatherBindingModel model,
                                  BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()){
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.weatherBindingModel",result);
            redirect.addFlashAttribute("weatherBindingModel",redirect);

        }else{
            WeatherViewModel weatherResult = this.service.getWeatherInformation(model.getCityName(), model.getUnit());
            redirect.addFlashAttribute("weatherViewModel",weatherResult);
        }


        return "redirect:/weatherAPI";
    }
}
