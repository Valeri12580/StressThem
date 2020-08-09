package com.stressthem.app.integrational;

import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.ArticleServiceModel;
import com.stressthem.app.domain.models.service.AttackServiceModel;
import com.stressthem.app.domain.models.view.ArticleViewModel;
import com.stressthem.app.domain.models.view.AttackViewModel;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.services.interfaces.ArticleService;
import com.stressthem.app.services.interfaces.AttackService;
import com.stressthem.app.services.interfaces.UserService;
import com.stressthem.app.web.controllers.ArticleController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ArticleControllerTest extends ControllerTestBase {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private Principal principal;

    @MockBean
    private AttackService attackService;

    @MockBean
    private UserService userService;

    @MockBean
    private BindingResult bindingResult;



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

//    @Test
//    @WithMockUser(username = "valeri12580")
//    public void testLaunch() throws Exception {
//
////        Mockito.when(((User)authentication.getPrincipal()).getId()).thenReturn("1");
//        Mockito.when(((User)principal).getId()).thenReturn("1");
//
//        Mockito.when(userService.hasUserActivePlan("valeri12580")).thenReturn(true);
//
//        Mockito.when(attackService.getAllAttacksForCurrentUser("valeri12580")).thenReturn(List.of(new AttackServiceModel()));
//        Mockito.when(modelMapper.map(attackService.getAllAttacksForCurrentUser("valeri12580"),
//                AttackViewModel[].class)).thenReturn(new AttackViewModel[]{new AttackViewModel(),new AttackViewModel()});
//        Mockito.when(userService.getUserAvailableAttacks("valeri12580")).thenReturn(30);
//
//
//
//        super.mockMvc.perform(get("/home/launch"))
//                .andExpect(view().name("home-launch-attack"));
//
//    }

//    @Test
//    @WithMockUser(username = "valeri12580")
//    public void postLaunchShouldReject_UserActivePlanDoesntExists() throws Exception {
////        Mockito.when(principal.getName()).thenReturn("valeri12580");
//        Mockito.when(userService.hasUserActivePlan("valeri12580"))
//                .thenReturn(false);
//
//        mockMvc.perform(post("/home/launch").with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/home/launch?favicon=%5Cassets%5Cimg%5Cfavicon.png"));
//
//        Mockito.verify(bindingResult).reject("errorCode1","You dont have an active plan!");
//
//    }

}
