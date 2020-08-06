package com.stressthem.app.integrational;

import com.stressthem.app.domain.models.service.ArticleServiceModel;
import com.stressthem.app.domain.models.view.ArticleViewModel;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.services.interfaces.ArticleService;
import com.stressthem.app.web.controllers.ArticleController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ArticleControllerTest extends ControllerTestBase {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ArticleService articleService;



    @Test
    public void testAllArticles() throws Exception {

        Mockito.when(this.articleService.getAllArticles()).thenReturn(List.of(new ArticleServiceModel(),new ArticleServiceModel()));
        Mockito.when(modelMapper.map(this.articleService.getAllArticles(),ArticleViewModel[].class)).thenReturn(
                new ArticleViewModel[]{new ArticleViewModel(),new ArticleViewModel()}
        );
        super.mockMvc.perform(get("/articles"))
                .andExpect(view().name("articles"))
                .andExpect(model().attributeExists("articles"));

    }

    @Test
    public void testArticleDetails() throws Exception {
        Mockito.when(articleService.getArticleById("1")).thenReturn(new ArticleServiceModel());
        Mockito.when(modelMapper.map(articleService.getArticleById("1"), ArticleViewModel.class))
                .thenReturn(new ArticleViewModel());


        super.mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("article-details"))
                .andExpect(model().attributeExists("article"));

    }

    @Test
    @WithMockUser(username = "valeri125800",authorities = {"ROOT"})
    public void testArticleDelete() throws Exception {


        super.mockMvc.perform(get("/articles/delete/1")).
                andExpect(redirectedUrl("/articles?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

    }

}
