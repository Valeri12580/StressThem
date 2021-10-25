package com.stressthem.app.domain.models.binding;

import com.stressthem.app.domain.TempUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class WeatherBindingModel {

    @NotEmpty(message = "Can't be empty")
    @Size(max = 20, message = "The maximum city length must be 20 symbols")
    private String cityName;

    @NotNull(message = "Please choose unit!")
    private TempUnit unit;
}
