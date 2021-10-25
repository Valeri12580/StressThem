package com.stressthem.app.services;

import com.google.gson.Gson;
import com.stressthem.app.domain.TempUnit;
import com.stressthem.app.domain.models.view.weather.WeatherViewModel;
import com.stressthem.app.services.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.api.url}")
    private  String OPEN_WEATHER_MAP_API_URL;

    private RestTemplate restTemplate;

    private Gson gson;

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    @Override
    public WeatherViewModel getWeatherInformation(String city, TempUnit unit) {
        String formatted = String.format(OPEN_WEATHER_MAP_API_URL,city,unit.getValue());
        String json = restTemplate.getForObject(formatted, String.class);
        return this.gson.fromJson(json, WeatherViewModel.class);
    }
}
