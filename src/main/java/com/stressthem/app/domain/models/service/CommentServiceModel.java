package com.stressthem.app.domain.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentServiceModel extends BaseServiceModel {

    private int rate;

    private String description;

    private UserServiceModel user;
}
