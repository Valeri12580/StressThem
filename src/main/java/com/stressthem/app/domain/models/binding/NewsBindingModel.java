package com.stressthem.app.domain.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewsBindingModel {

    @NotEmpty(message = "Can't be empty")
    @Size(max = 25, message = "The maximum length must be 25 symbols")
    private String keyword;
}
