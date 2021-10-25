package com.stressthem.app.services.interfaces;

import com.stressthem.app.domain.TempUnit;
import com.stressthem.app.domain.models.view.weather.WeatherViewModel;

public interface WeatherService {

    WeatherViewModel getWeatherInformation(String city, TempUnit unit);
}
