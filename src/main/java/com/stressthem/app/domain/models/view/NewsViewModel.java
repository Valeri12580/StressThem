package com.stressthem.app.domain.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewsViewModel {

    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

}
