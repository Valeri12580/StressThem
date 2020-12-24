package com.stressthem.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CommentBindingModel {

    @Min(value = 1,message = "The rating must be >= 1")
    @Max(value = 5,message = "The rating must be <=5")
    private int rate;

    @Size(min = 10,max = 100,message = "The size must be between 10 and 100 characters")
    private String description;
}
