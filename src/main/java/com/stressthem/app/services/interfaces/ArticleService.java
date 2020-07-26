package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.service.ArticleServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface ArticleService {

    List<ArticleServiceModel>getAllArticles();

    void deleteArticleById(String id);

    ArticleServiceModel getArticleById(String id) throws NotFoundException;

    ArticleServiceModel registerArticle(ArticleServiceModel articleServiceModel,String username);
}
