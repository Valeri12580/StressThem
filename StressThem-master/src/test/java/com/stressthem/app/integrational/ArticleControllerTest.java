package com.stressthem.app.integrational;

import com.stressthem.app.domain.models.view.ArticleViewModel;
import com.stressthem.app.integrational.base.ControllerTestBase;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.services.interfaces.ArticleService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ArticleControllerTest extends ControllerTestBase {



    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testAllArticles() throws Exception {


        super.mockMvc.perform(get("/articles"))
                .andExpect(view().name("articles"))
                .andExpect(model().attributeExists("articles"));

    }

    @Test
    public void testArticleDetails() throws Exception {

        ArticleViewModel articleViewModel =
                this.modelMapper
                        .map(articleService.getAllArticles().get(0), ArticleViewModel.class);

        super.mockMvc.perform(get(String.format("/articles/%s", articleViewModel.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("article-details"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attribute("article", Matchers.hasProperty("id"
                        , Matchers.equalToIgnoringCase(articleViewModel.getId()))));

    }

    @Test
    @WithMockUser(username = "valeri125800", authorities = {"ROOT"})
    public void testArticleDelete() throws Exception {

        String articleId=articleService.getAllArticles().get(0).getId();



        super.mockMvc.perform(get(String.format("/articles/delete/%s",articleId ))).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/articles?favicon=%5Cassets%5Cimg%5Cfavicon.png"));

        assertEquals(1,articleRepository.count());

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
