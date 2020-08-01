package com.stressthem.app.unit.services;

import com.stressthem.app.domain.entities.Article;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.ArticleServiceModel;
import com.stressthem.app.domain.models.service.UserServiceModel;
import com.stressthem.app.exceptions.ArticleNotFoundException;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.services.ArticleServiceImpl;
import com.stressthem.app.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article articleOne;

    private User user;

    private ArticleServiceModel articleServiceModel;

    @BeforeEach
    public void init() {
        this.user = new User();
        this.user.setUsername("valeri");
        this.user.setId("1");

        this.articleOne = new Article();
        this.articleOne.setId("1");
        this.articleOne.setTitle("Test");
        this.articleOne.setAuthor(user);

        this.articleServiceModel = new ArticleServiceModel();

        this.articleServiceModel.setId("1");
        this.articleServiceModel.setTitle("Test");
        this.articleServiceModel.setAuthor(user);

    }

    @Test
    public void getAllArticlesShouldReturnCorrect() {
        Mockito.when(articleRepository.findAllByOrderByAddedOnDesc()).thenReturn(List.of(articleOne));
        Mockito.when(modelMapper.map(articleRepository.findAllByOrderByAddedOnDesc(), ArticleServiceModel[].class))
                .thenReturn(List.of(articleServiceModel).toArray(ArticleServiceModel[]::new));
        List<ArticleServiceModel> actual = this.articleService.getAllArticles();

        assertEquals(1, actual.size());
        assertEquals(articleServiceModel, actual.get(0));

    }

    @Test
    @WithMockUser(roles = {"ROOT", "ADMIN"})
    public void deleteArticleByIdShouldWork(){
        this.articleService.deleteArticleById("1");

        Mockito.verify(articleRepository).deleteById("1");
    }

    @Test
    public void getArticleByIdShouldReturnArticle_IdCorrect(){
        Mockito.when(articleRepository.findById("1")).thenReturn(Optional.of(articleOne));
        Mockito.when(modelMapper.map(articleRepository.findById("1").get(),ArticleServiceModel.class))
                .thenReturn(articleServiceModel);
        ArticleServiceModel actual=articleService.getArticleById("1");
        assertEquals(articleServiceModel,actual);

    }

    @Test
    public void getArticleByIdShouldReturnException_IdIncorrect(){
        Mockito.when(articleRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class,()->{
            this.articleService.getArticleById("2");
        });
    }

    @Test
    public void registerArticleShouldWork(){
        Mockito.when(userService.getUserByUsername("valeri")).thenReturn(new UserServiceModel());

        Mockito.when(modelMapper.map(new UserServiceModel(),User.class)).thenReturn(user);
        Mockito.when(modelMapper.map(articleServiceModel,Article.class)).thenReturn(articleOne);

        this.articleService.registerArticle(articleServiceModel,"valeri");
        Mockito.verify(articleRepository).save(articleOne);

    }

}
