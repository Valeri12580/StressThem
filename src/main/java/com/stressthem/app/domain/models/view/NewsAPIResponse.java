package com.stressthem.app.domain.models.view;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsAPIResponse {
    private String status;
    private int totalResults;
    private List<NewsViewModel> articles;
}
