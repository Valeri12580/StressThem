package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.view.CryptocurrencyViewModel;
import com.stressthem.app.services.interfaces.CryptocurrencyService;
import com.stressthem.app.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {
    private CryptocurrencyService cryptocurrencyService;
    private ModelMapper modelMapper;

    @Autowired
    public CurrencyController(CryptocurrencyService cryptocurrencyService, ModelMapper modelMapper) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("All currencies")
    @GetMapping
    public String currencies(Model model){
        model.addAttribute("cryptos", Arrays.asList(this.modelMapper.map(this.cryptocurrencyService.getAllCryptocurrencies(), CryptocurrencyViewModel[].class)));
        return "currencies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCrypto(@PathVariable String id){
        this.cryptocurrencyService.deleteById(id);
        return "redirect:/currencies";
    }
}
