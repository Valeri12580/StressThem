package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.models.view.NewsViewModel;

import java.util.List;

public interface NewsService {

    List<NewsViewModel> getAllNews(String keyword);
}
