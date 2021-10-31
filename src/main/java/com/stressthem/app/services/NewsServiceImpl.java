package com.stressthem.app.services;

import com.google.gson.Gson;
import com.stressthem.app.domain.models.view.NewsAPIResponse;
import com.stressthem.app.domain.models.view.NewsViewModel;
import com.stressthem.app.services.interfaces.NewsService;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Value("${news.api.url}")
    private String NEWS_API_URL;

    private RestTemplate template;

    private Gson gson;

    @Autowired
    public NewsServiceImpl(RestTemplate template, Gson gson) {
        this.template = template;
        this.gson = gson;
    }

    @Override
    public List<NewsViewModel> getAllNews(String keyword) {
        String formatted = String.format(NEWS_API_URL, keyword);
        String json = template.getForObject(formatted, String.class);
        NewsAPIResponse newsAPIResponse = this.gson.fromJson(json, NewsAPIResponse.class);
        return newsAPIResponse.getArticles();
    }
}
