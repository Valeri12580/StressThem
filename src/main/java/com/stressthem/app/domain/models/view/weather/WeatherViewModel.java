package com.stressthem.app.domain.models.view.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherViewModel {
    public Main main;
    public Sys sys;
    public String name;


}
