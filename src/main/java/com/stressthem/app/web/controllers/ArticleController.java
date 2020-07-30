package com.stressthem.app.web.controllers;

import com.stressthem.app.domain.models.view.ArticleViewModel;
import com.stressthem.app.services.interfaces.ArticleService;
import com.stressthem.app.web.annotations.PageTitle;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private ArticleService articleService;
    private ModelMapper modelMapper;


    @Autowired
    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @PageTitle("Articles")
    @GetMapping
    public String allArticles(Model model){
        List<ArticleViewModel> articles = Arrays.asList(this.modelMapper.map(this.articleService.getAllArticles(), ArticleViewModel[].class));
        model.addAttribute("articles", articles);

        return "articles";
    }

    @PageTitle("Articles")
    @GetMapping("/{id}")
    public String articleDetails(@PathVariable String id, Model model) throws NotFoundException {
        ArticleViewModel article = this.modelMapper.map(this.articleService.getArticleById(id), ArticleViewModel.class);
        model.addAttribute("article",article);
        return "article-details";
    }


    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable String id){
        this.articleService.deleteArticleById(id);
        return "redirect:/articles";
    }

}
