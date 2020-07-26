package com.stressthem.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AnnouncementBindingModel {

    @Size(min = 8,max = 40,message = "The title must be between 8 and 40 characters!")
    private String title;

    @Size(min = 8,max = 100,message = "The description must be between 8 and 100 characters!")
    private String description;
}
