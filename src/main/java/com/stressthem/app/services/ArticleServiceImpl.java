package com.stressthem.app.services;

import com.stressthem.app.domain.entities.Article;
import com.stressthem.app.domain.models.service.ArticleServiceModel;
import com.stressthem.app.repositories.ArticleRepository;
import com.stressthem.app.services.interfaces.ArticleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl  implements ArticleService {
    private ArticleRepository articleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
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
    public ArticleServiceModel getArticleById(String id) throws NotFoundException {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("The article is not found"));

        return this.modelMapper.map(article,ArticleServiceModel.class);
    }
}
