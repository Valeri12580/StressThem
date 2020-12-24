package com.stressthem.app.domain.models.service;


import com.stressthem.app.domain.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CryptocurrencyServiceModel extends BaseServiceModel {

    String title;


    private String description;


    private User author;


    private LocalDateTime addedOn;


    private String imageUrl;
}
