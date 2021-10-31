package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.binding.NewsBindingModel;
import com.stressthem.app.domain.models.binding.WeatherBindingModel;
import com.stressthem.app.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/newsAPI")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String getNewsPage(Model model) {
        if (!model.containsAttribute("newsBindingModel")) {
            model.addAttribute("newsBindingModel", new NewsBindingModel());
        }
        return "news";
    }

    @GetMapping("/news")
    public String getNews(@Valid @ModelAttribute NewsBindingModel model,
                          BindingResult result, RedirectAttributes redirect) {

        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.newsBindingModel", result);
            redirect.addFlashAttribute("newsBindingModel", model);
        }

        try {
            redirect.addFlashAttribute("newsViewModel", newsService.getAllNews(model.getKeyword()));
        } catch (Exception ex) {
            result.rejectValue("keyword", "keyword", "News with that keyword doesn't exists");

        }
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.newsBindingModel", result);
            redirect.addFlashAttribute("newsBindingModel", model);
        }

        return "redirect:/newsAPI";

    }
}
