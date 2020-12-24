package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Article;
import com.stressthem.app.domain.entities.User;
import com.stressthem.app.domain.models.service.ArticleServiceModel;
import com.stressthem.app.exceptions.ArticleNotFoundException;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.services.interfaces.ArticleService;
import com.stressthem.app.services.interfaces.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl  implements ArticleService {
    private ArticleRepository articleRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper, UserService userService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<ArticleServiceModel> getAllArticles() {
        return Arrays.asList(this.modelMapper.map(this.articleRepository.findAllByOrderByAddedOnDesc(),ArticleServiceModel[].class));
    }

    @Override
    public void deleteArticleById(String id) {
        this.articleRepository.deleteById(id);
    }

    @Override
    public ArticleServiceModel getArticleById(String id)  {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("The article is not found"));

        return this.modelMapper.map(article,ArticleServiceModel.class);
    }

    @Override
    public ArticleServiceModel registerArticle(ArticleServiceModel articleServiceModel, String username) {
        User user=this.modelMapper.map(this.userService.getUserByUsername(username),User.class);

        Article article=this.modelMapper.map(articleServiceModel,Article.class);

        article.setAddedOn(LocalDateTime.now(ZoneId.systemDefault()));
        article.setAuthor(user);

        this.articleRepository.save(article);
        return this.modelMapper.map(article,ArticleServiceModel.class);
    }
}
